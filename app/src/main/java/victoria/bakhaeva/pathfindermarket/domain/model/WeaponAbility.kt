package victoria.bakhaeva.pathfindermarket.domain.model

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
    val book: Book? = null,
)

internal fun bonusAbility(bonus: Int) = WeaponAbility(
    alias = "bonus_$bonus",
    name = "+$bonus",
    engName = "+$bonus",
    aura = "",
    cl = 0,
    bonusPrice = bonus,
    moneyPrice = null,
    description = "Бонус к атаке +$bonus",
    constructionRequirements = "",
)
