package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import victoria.bakhaeva.pathfindermarket.data.model.Ability
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.mastercraftPrice

data class WeaponUpgradeState(
    val weapon: Weapon,
    val isMasterwork: Boolean,
    val abilities: List<Ability>
)

fun WeaponUpgradeState.countSum(): Double =
    (weapon.cost ?: 0.0) +
            abilities.sumOf { it.moneyPrice ?: 0 } +
            if (isMasterwork) mastercraftPrice else 0.0

fun WeaponUpgradeState.countAttackBonus(): Int =
    if (isMasterwork) 1 else 0