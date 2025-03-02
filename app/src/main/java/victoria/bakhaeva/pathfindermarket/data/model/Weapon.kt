package victoria.bakhaeva.pathfindermarket.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Weapon(
    val alias: String,
    val name: String? = null,
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
    val proficientCategory: ProficientCategory,
    val rangeCategory: RangeCategory,
    val encumbranceCategory: EncumbranceCategory? = null,
    val parents: List<String>? = null,
    val childs: List<Weapon>? = null
)
@Serializable
data class ProficientCategory(
    val name: String? = null,
    val alias: String,
)
@Serializable
data class RangeCategory(
    val name: String? = null,
    val alias: String,
)
@Serializable
data class EncumbranceCategory(
    val name: String? = null,
    val alias: String,
)
@Serializable
enum class Encumbrance(val value: String) {
    LIGHT("light"),
    ONE_HANDED("oneHanded"),
    TWO_HANDED("twoHanded"),
    SIEGE("siege"),
}
@Serializable
enum class Proficient(val value: String) {
    AMMUNITION("ammunition"),
    SIMPLE("simple"),
    MARTIAL("martial"),
    EXOTIC("exotic"),
    EARLY_FIREARM("earlyFirearm"),
    ALCHEMICAL("alchemical"),
    ADVANCED_FIREARM("advancedFirearm"),
}
@Serializable
enum class Range(val value: String) {
    MELEE("melee"),
    RANGED("ranged"),

}