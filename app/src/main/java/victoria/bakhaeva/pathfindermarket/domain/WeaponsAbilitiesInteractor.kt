package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.bonusAbility
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice.maxBonus
import javax.inject.Inject

internal class WeaponsAbilitiesInteractor @Inject constructor(
    private val pathfinderApi: PathfinderApi,
    private val weaponCache: WeaponCache,
) {

    fun getAbilities(range: String): Flow<List<WeaponAbility>> = flow {
        weaponCache.getWeaponAbilities()?.let { emit(it) }
        val query = when (range) {
            Range.MELEE.value -> "meeleWeapon"
            Range.RANGED.value -> "rangedWeapon"
            else -> throw Exception("Unknown range")
        }
        val abilities = pathfinderApi.getAbilities(query)
        if (!weaponCache.saveWeaponAbilities(abilities)) {
            emit(abilities)
        }
    }

    suspend fun getAbility(abilityAlias: String): WeaponAbility? =
        weaponCache.getWeaponAbility(abilityAlias)
}
