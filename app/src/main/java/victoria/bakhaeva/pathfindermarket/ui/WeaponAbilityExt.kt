package victoria.bakhaeva.pathfindermarket.ui

import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility

fun WeaponAbility.formatPrice() =
    moneyPrice?.formatCost()
        ?: bonusPrice?.let { "Бонус +$it" }
        ?: "???"
