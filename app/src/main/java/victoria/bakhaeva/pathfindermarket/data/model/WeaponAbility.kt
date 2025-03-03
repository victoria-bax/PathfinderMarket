package victoria.bakhaeva.pathfindermarket.data.model

import victoria.bakhaeva.pathfindermarket.ui.formatCost

data class WeaponAbility(
    val alias: String,
    val name: String,
    val engName: String,
    val aura: String,
    val cl: Int,
    val bonusPrice: Int?,
    val moneyPrice: Int?,
    val description: String,
    val constructionRequirements: String,
    val childs: List<WeaponAbility>?
)

fun bonusAbility(bonus: Int) = WeaponAbility(
    alias = "bonus_$bonus",
    name = "+$bonus",
    engName = "+$bonus",
    aura = "",
    cl = 0,
    bonusPrice = bonus,
    moneyPrice = null,
    description = "Бонус к атаке +$bonus",
    constructionRequirements = "",
    childs = null
)

fun WeaponAbility.formatPrice() =
    moneyPrice?.toDouble()?.formatCost()
        ?: bonusPrice?.let { "Бонус +$it" }
        ?: "???"