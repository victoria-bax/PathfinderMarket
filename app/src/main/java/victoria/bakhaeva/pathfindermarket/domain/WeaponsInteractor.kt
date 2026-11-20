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
        cached?.let { emit(it) }
        val mapped = weaponMapper.map(pathfinderApi.getWeapons())
        if (mapped != cached) {
            weaponCache.saveWeapons(mapped)
            emit(mapped)
        }
    }

    fun getWeapon(alias: String): Weapon? = weaponCache.getWeapon(alias)
}
