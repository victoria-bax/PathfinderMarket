package victoria.bakhaeva.pathfindermarket.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import victoria.bakhaeva.pathfindermarket.domain.model.Order

data class SortUiState(
    val selected: Order,
    val options: List<SortOptionUiState>,
)

data class SortOptionUiState(
    val order: Order,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
)
