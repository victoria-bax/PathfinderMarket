package victoria.bakhaeva.pathfindermarket.presentation.mapper

import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.AllFilters
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState
import javax.inject.Inject

class WeaponUiStateMapper @Inject constructor() {
    fun map(weapons: List<Weapon>): WeaponListUiState {
        return WeaponListUiState(
            weapons = weapons,
            allWeapons = weapons,
            sort = Order.DEFAULT,
            filterState = AllFilters(
                proficientCategories = weapons.map { it.proficientCategory }
                    .distinctBy { it.alias },
                rangeCategories = weapons.map { it.rangeCategory }.distinctBy { it.alias },
                encumbranceCategories = weapons.mapNotNull { it.encumbranceCategory }
                    .distinctBy { it.alias }
            ).let { allFilters ->
                FilterState(
                    allFilters = allFilters,
                    selectedFilters = allFilters.rangeCategories.map { it.alias }
                            + allFilters.proficientCategories.map { it.alias }
                            + allFilters.encumbranceCategories.map { it.alias }
                )
            }
        )
    }
}