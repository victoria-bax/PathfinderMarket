package victoria.bakhaeva.pathfindermarket.ui.weapons

import androidx.annotation.StringRes
import victoria.bakhaeva.pathfindermarket.R

enum class Order(@StringRes val labelRes: Int) {
    PRICE_ASC(R.string.sort_price_asc),
    PRICE_DESC(R.string.sort_price_desc),
    NAME_ASC(R.string.sort_name_asc),
    NAME_DESC(R.string.sort_name_desc),
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
