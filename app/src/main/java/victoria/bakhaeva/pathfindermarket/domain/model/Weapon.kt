package victoria.bakhaeva.pathfindermarket.domain.model

data class Weapon(
    val alias: String,
    val name: String,
    val engName: String? = null,
    val type: String? = null,
    val cost: Int? = null,
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
    val children: List<Weapon>? = null,
    val book: Book? = null,
)

data class Book(
    val alias: BookAlias,
    val name: String,
    val order: Int,
    val abbreviation: String,
)

data class ProficientCategory(val name: String? = null, val alias: ProficientAlias)
data class RangeCategory(val name: String? = null, val alias: RangeAlias)
data class EncumbranceCategory(val name: String? = null, val alias: EncumbranceAlias)
