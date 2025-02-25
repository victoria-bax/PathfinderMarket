package victoria.bakhaeva.pathfindermarket.data.model

data class WeaponProperty(
    val alias: String,
    val name: String,
    val engName: String,
    val aura: String,
    val cl: Int,
    val bonusPrice: Int,
    val moneyPrice: Double? = null,
    val description: String,
    val constructionRequirements: String,
)