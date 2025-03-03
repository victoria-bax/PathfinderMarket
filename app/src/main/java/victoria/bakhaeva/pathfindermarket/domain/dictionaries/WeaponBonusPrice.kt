package victoria.bakhaeva.pathfindermarket.domain.dictionaries

object WeaponBonusPrice {
    const val maxBonus = 5
    const val maxBonusSum = 10

    val prices =  mapOf(
        1 to 2000,
        2 to 8000,
        3 to 18000,
        4 to 32000,
        5 to 50000,
        6 to 72000,
        7 to 98000,
        8 to 128000,
        10 to 200000,
    )

    val mastercraftPrice = 300.0

    val masterCraftBonus = 1
}