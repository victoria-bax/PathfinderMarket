package victoria.bakhaeva.pathfindermarket.data.model

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
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
    val childs: List<WeaponResponse>? = null,
    val book: BookResponse? = null,
)

data class ProficientCategoryResponse(
    val name: String? = null,
    val alias: Proficient,
)

data class RangeCategoryResponse(
    val name: String? = null,
    val alias: Range,
)

data class EncumbranceCategoryResponse(
    val name: String? = null,
    val alias: Encumbrance,
)

data class BookResponse(
    val alias: BookAlias,
    val name: String,
    val order: Int,
    val abbreviation: String,
)

enum class Encumbrance(val value: String) {
    @SerializedName("light") LIGHT("light"),
    @SerializedName("oneHanded") ONE_HANDED("oneHanded"),
    @SerializedName("twoHanded") TWO_HANDED("twoHanded"),
    @SerializedName("siege") SIEGE("siege"),
}

enum class Proficient(val value: String) {
    @SerializedName("ammunition") AMMUNITION("ammunition"),
    @SerializedName("simple") SIMPLE("simple"),
    @SerializedName("martial") MARTIAL("martial"),
    @SerializedName("exotic") EXOTIC("exotic"),
    @SerializedName("earlyFirearm") EARLY_FIREARM("earlyFirearm"),
    @SerializedName("alchemical") ALCHEMICAL("alchemical"),
    @SerializedName("advancedFirearm") ADVANCED_FIREARM("advancedFirearm"),
}

enum class Range(val value: String, @DrawableRes val icon: Int) {
    @SerializedName("melee") MELEE("melee", R.drawable.ic_melee),
    @SerializedName("ranged") RANGED("ranged", R.drawable.ic_ranged),
}

enum class BookAlias(val value: String) {
    @SerializedName("coreRulebook") CORE_RULEBOOK("coreRulebook"),
    @SerializedName("advancedPlayerGuide") ADVANCED_PLAYER_GUIDE("advancedPlayerGuide"),
    @SerializedName("adventurersGuide") ADVENTURERS_GUIDE("adventurersGuide"),
    @SerializedName("ultimateCombat") ULTIMATE_COMBAT("ultimateCombat"),
    @SerializedName("ultimateEquipment") ULTIMATE_EQUIPMENT("ultimateEquipment"),
    @SerializedName("riseOfTheRunelords") RISE_OF_THE_RUNELORDS("riseOfTheRunelords"),
    @SerializedName("innerSeaWorldGuide") INNER_SEA_WORLD_GUIDE("innerSeaWorldGuide"),
    @SerializedName("varisiaBirthplaceOfLegends") VARISIA_BIRTHPLACE_OF_LEGENDS("varisiaBirthplaceOfLegends"),
    @SerializedName("shatteredStarShardsOfSin") SHATTERED_STAR_SHARDS_OF_SIN("shatteredStarShardsOfSin"),
}
