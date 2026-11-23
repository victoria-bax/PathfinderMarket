package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.mapper.WeaponMapper
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject

internal class WeaponsInteractor @Inject constructor(
    private val pathfinderApi: PathfinderApi,
    private val weaponCache: WeaponCache,
    private val weaponMapper: WeaponMapper,
) {

    fun getWeapons(): Flow<List<Weapon>> = flow {
        val cached = weaponCache.getWeapons()
        if (cached != null) {
            emit(cached)
        } else {
            val weapons = weaponMapper.map(pathfinderApi.getWeapons())
            emit(weapons)
            weaponCache.saveWeapons(weapons)
        }
    }

    suspend fun getWeapon(alias: String): Weapon? = weaponCache.getWeapon(alias)
}
