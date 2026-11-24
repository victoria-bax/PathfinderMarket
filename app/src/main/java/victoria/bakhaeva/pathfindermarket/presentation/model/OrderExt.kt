package victoria.bakhaeva.pathfindermarket.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.Order

val defaultSortUiState = SortUiState(
    selected = Order.DEFAULT,
    options = Order.entries.map { it.toUiState() },
)

@StringRes
internal fun Order.labelRes(): Int = when (this) {
    Order.PRICE_ASC -> R.string.sort_price_asc
    Order.PRICE_DESC -> R.string.sort_price_desc
    Order.NAME_ASC -> R.string.sort_name_asc
    Order.NAME_DESC -> R.string.sort_name_desc
}

@DrawableRes
internal fun Order.icon(): Int = when (this) {
    Order.PRICE_ASC -> R.drawable.sort_descending
    Order.PRICE_DESC -> R.drawable.sort_ascending
    Order.NAME_ASC -> R.drawable.sort_alphabetical
    Order.NAME_DESC -> R.drawable.sort_alphabetical_inverted
}

internal fun Order.toUiState() = SortOptionUiState(
    order = this,
    labelRes = labelRes(),
    iconRes = icon(),
)
