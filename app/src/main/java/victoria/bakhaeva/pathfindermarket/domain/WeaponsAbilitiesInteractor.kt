package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import javax.inject.Inject

internal class WeaponsAbilitiesInteractor @Inject constructor(
    private val weaponCache: WeaponCache,
) {

    suspend fun getAbilities(range: String): List<WeaponAbility>? {
        RangeAlias.entries.firstOrNull { it.value == range }
            ?: throw Exception("Unknown range: $range")
        return weaponCache.getWeaponAbilities(range)
    }

    suspend fun getAbility(abilityAlias: String): WeaponAbility? =
        weaponCache.getWeaponAbility(abilityAlias)
}
