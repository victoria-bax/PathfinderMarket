package victoria.bakhaeva.pathfindermarket.data.model

data class Ability(
    val alias: String,
    val name: String,
    val engName: String,
    val aura: String,
    val cl: Int,
    val bonusPrice: Int?,
    val moneyPrice: Int?,
    val description: String,
    val constructionRequirements: String,
    val childs: List<Ability>?
)

fun bonusAbility(bonus: Int) = Ability(
    alias = "bonus_$bonus",
    name = "+$bonus",
    engName = "+$bonus",
    aura = "",
    cl = 0,
    bonusPrice = bonus,
    moneyPrice = null,
    description = "",
    constructionRequirements = "",
    childs = null
)