package victoria.bakhaeva.pathfindermarket.domain.model

enum class EncumbranceAlias(val value: String) {
    LIGHT("light"),
    ONE_HANDED("oneHanded"),
    TWO_HANDED("twoHanded"),
    SIEGE("siege"),
}

enum class ProficientAlias(val value: String) {
    AMMUNITION("ammunition"),
    SIMPLE("simple"),
    MARTIAL("martial"),
    EXOTIC("exotic"),
    EARLY_FIREARM("earlyFirearm"),
    ALCHEMICAL("alchemical"),
    ADVANCED_FIREARM("advancedFirearm"),
}

enum class RangeAlias(val value: String) {
    MELEE("melee"),
    RANGED("ranged"),
}

enum class BookAlias(val value: String) {
    CORE_RULEBOOK("coreRulebook"),
    ADVANCED_PLAYER_GUIDE("advancedPlayerGuide"),
    ADVENTURERS_GUIDE("adventurersGuide"),
    ULTIMATE_COMBAT("ultimateCombat"),
    ULTIMATE_EQUIPMENT("ultimateEquipment"),
    RISE_OF_THE_RUNELORDS("riseOfTheRunelords"),
    INNER_SEA_WORLD_GUIDE("innerSeaWorldGuide"),
    VARISIA_BIRTHPLACE_OF_LEGENDS("varisiaBirthplaceOfLegends"),
    SHATTERED_STAR_SHARDS_OF_SIN("shatteredStarShardsOfSin"),
}
