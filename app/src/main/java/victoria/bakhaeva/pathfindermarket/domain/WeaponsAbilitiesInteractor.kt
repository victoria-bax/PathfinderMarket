package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import javax.inject.Inject

internal class WeaponsAbilitiesInteractor @Inject constructor(
    private val weaponCache: WeaponCache,
) {

    fun getAbilities(range: String): Flow<List<WeaponAbility>> = flow {
        if (range != Range.MELEE.value && range != Range.RANGED.value)
            throw Exception("Unknown range: $range")
        weaponCache.getWeaponAbilities(range)?.let { emit(it) }
    }

    suspend fun getAbility(abilityAlias: String): WeaponAbility? =
        weaponCache.getWeaponAbility(abilityAlias)
}
