package victoria.bakhaeva.pathfindermarket.presentation.model

import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

data class WeaponListUiState(
    val weapons: List<Weapon>,
    val allWeapons: List<Weapon>,
    val sort: Order,
    val filterState: FilterState,
)
