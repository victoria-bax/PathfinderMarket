package victoria.bakhaeva.pathfindermarket.data.model

data class Weapon(
    val alias: String,
    val name: String,
    val engName: String,
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
    val description: String,
    val proficientCategory: ProficientCategory,
    val rangeCategory: RangeCategory,
    val encumbranceCategory: EncumbranceCategory? = null,
    val parents: List<String>? = null,
    val book: Book,
    val childs: List<Weapon>? = null
)

data class ProficientCategory(
    val name: String,
    val alias: String
)

data class RangeCategory(
    val name: String,
    val alias: String
)

data class EncumbranceCategory(
    val name: String,
    val alias: String
)

data class Book(
    val alias: String,
    val name: String,
    val order: Int,
    val abbreviation: String
)
