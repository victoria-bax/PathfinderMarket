package victoria.bakhaeva.pathfindermarket.presentation.model

import androidx.annotation.DrawableRes
import victoria.bakhaeva.pathfindermarket.ui.weapons.state.FilterState

data class WeaponListUiState(
    val weapons: List<WeaponListItemUiState>,
    val sort: SortUiState,
    val filterState: FilterState,
)

data class WeaponListItemUiState(
    val alias: String,
    val name: String,
    val cost: Int?,
    val description: String?,
    @DrawableRes val rangeIconRes: Int,
    val proficientCategoryName: String?,
    val encumbranceCategoryName: String?,
)
