package victoria.bakhaeva.pathfindermarket.ui

fun Double?.formatCost() = when (this) {
    null -> "0"
    -1.0 -> "Особая"
    0.0 -> "Бесплатно"
    else -> formatCoins()
}

private fun Double.formatCoins(): String {
    val convertToBronze = (this * 100).toInt()
    val gold = convertToBronze / 100
    val silver = (convertToBronze - gold * 100) / 10
    val bronze = convertToBronze - gold * 100 - silver * 10
    return StringBuilder()
        .apply {
            if (gold > 0) {
                append("$gold зм")
            }
            if (silver > 0) {
                append("$silver cм")
            }
            if (bronze > 0) {
                append("$bronze мм")
            }
        }.toString()
}
