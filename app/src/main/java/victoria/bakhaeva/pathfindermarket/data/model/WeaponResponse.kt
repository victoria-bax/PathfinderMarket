package victoria.bakhaeva.pathfindermarket.data.model

import com.google.gson.annotations.SerializedName
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias

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
    val childs: List<WeaponResponse>? = null,
    val book: BookResponse? = null,
)

data class ProficientCategoryResponse(
    val name: String? = null,
    val alias: ProficientAlias,
)

data class RangeCategoryResponse(
    val name: String? = null,
    val alias: RangeAlias,
)

data class EncumbranceCategoryResponse(
    val name: String? = null,
    val alias: EncumbranceAlias,
)

data class BookResponse(
    val alias: BookAlias,
    val name: String,
    val order: Int,
    val abbreviation: String,
)
