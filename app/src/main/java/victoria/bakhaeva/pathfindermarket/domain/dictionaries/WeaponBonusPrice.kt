package victoria.bakhaeva.pathfindermarket.domain.dictionaries

internal object WeaponBonusPrice {
    const val maxBonus = 5

    val prices = mapOf(
        1 to 200000,
        2 to 800000,
        3 to 1800000,
        4 to 3200000,
        5 to 5000000,
        6 to 7200000,
        7 to 9800000,
        8 to 12800000,
        10 to 20000000,
    )

    val mastercraftPrice = 30000
}