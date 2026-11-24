package victoria.bakhaeva.pathfindermarket.data.db

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.ApplicationScope
import timber.log.Timber
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.mapper.WeaponMapper
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.WeaponResponse
import victoria.bakhaeva.pathfindermarket.domain.WeaponCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DatabaseSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weaponCache: WeaponCache,
    private val weaponMapper: WeaponMapper,
    private val pathfinderApi: PathfinderApi,
    private val gson: Gson,
    @ApplicationScope private val appScope: CoroutineScope,
) {
    fun seedIfEmpty() {
        appScope.launch {
            seedWeapons()
            seedAbilities()
            refreshWeaponsFromApi()
            refreshAbilitiesFromApi()
        }
    }

    private suspend fun seedWeapons() {
        if (weaponCache.getWeapons() != null) return
        val type = object : TypeToken<List<WeaponResponse>>() {}.type
        val responses: List<WeaponResponse> = gson.fromJson(
            context.assets.open("weapons.json").bufferedReader().readText(), type
        )
        weaponCache.saveWeapons(weaponMapper.map(responses))
    }

    private suspend fun seedAbilities() {
        val type = object : TypeToken<List<WeaponAbility>>() {}.type
        if (weaponCache.getWeaponAbilities(MELEE) == null) {
            val melee: List<WeaponAbility> = gson.fromJson(
                context.assets.open("abilities_melee.json").bufferedReader().readText(), type
            )
            if (melee.isNotEmpty()) weaponCache.saveWeaponAbilities(MELEE, melee.convertPrices())
        }
        if (weaponCache.getWeaponAbilities(RANGED) == null) {
            val ranged: List<WeaponAbility> = gson.fromJson(
                context.assets.open("abilities_ranged.json").bufferedReader().readText(), type
            )
            if (ranged.isNotEmpty()) weaponCache.saveWeaponAbilities(RANGED, ranged.convertPrices())
        }
    }

    private suspend fun refreshWeaponsFromApi() {
        try {
            val weapons = weaponMapper.map(pathfinderApi.getWeapons())
            weaponCache.saveWeapons(weapons)
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh weapons from API, using cached data")
        }
    }

    private suspend fun refreshAbilitiesFromApi() {
        try {
            val melee = pathfinderApi.getAbilities("meeleWeapon")
            if (melee.isNotEmpty()) weaponCache.saveWeaponAbilities(MELEE, melee.convertPrices())
            val ranged = pathfinderApi.getAbilities("rangeWeapon")
            if (ranged.isNotEmpty()) weaponCache.saveWeaponAbilities(RANGED, ranged.convertPrices())
        } catch (e: Exception) {
            Timber.e(e, "Failed to refresh abilities from API, using cached data")
        }
    }

    private fun List<WeaponAbility>.convertPrices() = map {
        it.copy(moneyPrice = it.moneyPrice?.let { gp -> gp * 100 })
    }

    companion object {
        const val MELEE = "melee"
        const val RANGED = "ranged"
    }
}
