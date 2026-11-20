package victoria.bakhaeva.pathfindermarket.data.model

import androidx.annotation.DrawableRes
import victoria.bakhaeva.pathfindermarket.R

data class WeaponResponse(
    val alias: String,
    val name: String,
    val engName: String? = null,
    val type: String? = null,
    val cost: Double? = null,
    val damageS: String? = null,
    val damageM: String? = null,
    val criticalRoll: String? = null,
    val criticalDamage: String? = null,
    val range: String? = null,
    val misfire: String? = null,
    val capacity: String? = null,
    val weight: Double? = null,
    val special: String? = null,
    val description: String? = null,
    val proficientCategory: ProficientCategoryResponse,
    val rangeCategory: RangeCategoryResponse,
    val encumbranceCategory: EncumbranceCategoryResponse? = null,
    val parents: List<String>? = null,
    val childs: List<WeaponResponse>? = null
)

data class ProficientCategoryResponse(
    val name: String? = null,
    val alias: String,
)

data class RangeCategoryResponse(
    val name: String? = null,
    val alias: String,
)

data class EncumbranceCategoryResponse(
    val name: String? = null,
    val alias: String,
)

enum class Encumbrance(val value: String) {
    LIGHT("light"),
    ONE_HANDED("oneHanded"),
    TWO_HANDED("twoHanded"),
    SIEGE("siege"),
}

enum class Proficient(val value: String) {
    AMMUNITION("ammunition"),
    SIMPLE("simple"),
    MARTIAL("martial"),
    EXOTIC("exotic"),
    EARLY_FIREARM("earlyFirearm"),
    ALCHEMICAL("alchemical"),
    ADVANCED_FIREARM("advancedFirearm"),
}

enum class Range(val value: String, @DrawableRes val icon: Int) {
    MELEE("melee", R.drawable.ic_melee),
    RANGED("ranged", R.drawable.ic_ranged),
}
