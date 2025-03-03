package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import victoria.bakhaeva.pathfindermarket.data.model.Ability
import victoria.bakhaeva.pathfindermarket.data.model.Weapon

data class WeaponUpgradeState(
    val weapon: Weapon,
    val isMasterwork: Boolean,
    val sum: Double,
    val abilities: List<Ability>
)