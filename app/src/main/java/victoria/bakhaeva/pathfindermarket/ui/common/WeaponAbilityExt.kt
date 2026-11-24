package victoria.bakhaeva.pathfindermarket.ui.common

import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility

internal fun WeaponAbility.formatPrice() =
    moneyPrice?.formatCost()
        ?: bonusPrice?.let { "Бонус +$it" }
        ?: "???"
