package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject

internal class WeaponsInteractor @Inject constructor(
    private val weaponCache: WeaponCache,
) {

    fun getWeapons(): Flow<List<Weapon>> = flow {
        weaponCache.getWeapons()?.let { emit(it) }
    }

    suspend fun getWeapon(alias: String): Weapon? = weaponCache.getWeapon(alias)
}
