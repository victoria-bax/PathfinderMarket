package victoria.bakhaeva.pathfindermarket.presentation.mapper

import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import javax.inject.Inject

class WeaponUiStateMapper @Inject constructor() {
    fun map(weapons: List<Weapon>): WeaponListUiState {
        return WeaponListUiState(
            weapons = weapons,
            allWeapons = weapons,
            sort = Order.DEFAULT,
            proficientCategories = weapons.map { it.proficientCategory }.distinctBy { it.alias },
            rangeCategories = weapons.map { it.rangeCategory }.distinctBy { it.alias },
            encumbranceCategories = weapons.mapNotNull { it.encumbranceCategory }
                .distinctBy { it.alias }
        )
    }
}