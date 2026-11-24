package victoria.bakhaeva.pathfindermarket.presentation.mapper

import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListItemUiState
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.weapons.Order
import victoria.bakhaeva.pathfindermarket.ui.weapons.Range
import victoria.bakhaeva.pathfindermarket.ui.weapons.state.FilterState
import javax.inject.Inject

class WeaponUiStateMapper @Inject constructor() {
    fun map(weapons: List<Weapon>): WeaponListUiState {
        return WeaponListUiState(
            weapons = weapons.map { it.toUiState() },
            sort = Order.DEFAULT,
            filterState = AllFilters(
                proficientCategories = weapons.map { it.proficientCategory }
                    .distinctBy { it.alias },
                rangeCategories = weapons.map { it.rangeCategory }.distinctBy { it.alias },
                encumbranceCategories = weapons.mapNotNull { it.encumbranceCategory }
                    .distinctBy { it.alias },
                books = weapons.mapNotNull { it.book }
                    .distinctBy { it.alias }
                    .sortedBy { it.order },
            ).let { allFilters ->
                FilterState(
                    allFilters = allFilters,
                    selectedFilters = emptyList()
                )
            }
        )
    }

    fun List<Weapon>.toUiStates(): List<WeaponListItemUiState> = map { it.toUiState() }

    fun Weapon.toUiState() = WeaponListItemUiState(
        alias = alias,
        name = name,
        cost = cost,
        description = description,
        rangeIconRes = Range.from(rangeCategory.alias).icon,
        proficientCategoryName = proficientCategory.name,
        encumbranceCategoryName = encumbranceCategory?.name,
    )
}
