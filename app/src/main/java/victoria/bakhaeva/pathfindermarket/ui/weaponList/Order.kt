package victoria.bakhaeva.pathfindermarket.ui.weaponList

import victoria.bakhaeva.pathfindermarket.R

enum class Order(val value: String) {
    PRICE_ASC("По возрастанию цены"),
    PRICE_DESC("По убыванию цены"),
    NAME_ASC("По алфавиту"),
    NAME_DESC("По алфавиту (обратно)"),
    ;

    companion object {
        val DEFAULT = PRICE_DESC
    }
}

fun Order.icon() = when (this) {
    Order.PRICE_ASC -> R.drawable.sort_descending
    Order.PRICE_DESC -> R.drawable.sort_ascending
    Order.NAME_ASC -> R.drawable.sort_alphabetical
    Order.NAME_DESC -> R.drawable.sort_alphabetical_inverted

}