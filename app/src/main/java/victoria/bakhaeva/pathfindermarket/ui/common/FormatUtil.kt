package victoria.bakhaeva.pathfindermarket.ui.common

internal fun Int?.formatCost() = when (this) {
    null -> "0"
    -1 -> "Особая"
    0 -> "Бесплатно"
    else -> formatCoins()
}

private fun Int.formatCoins(): String {
    val gold = this / 100
    val silver = (this % 100) / 10
    val bronze = this % 10
    return StringBuilder()
        .apply {
            if (gold > 0) append("$gold зм")
            if (silver > 0) append("$silver см")
            if (bronze > 0) append("$bronze мм")
        }.toString()
}
