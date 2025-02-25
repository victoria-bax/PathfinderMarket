package victoria.bakhaeva.pathfindermarket.presentation.model

import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order

data class WeaponListUiState(
    val weapons: List<Weapon>,
    val allWeapons: List<Weapon>,
    val sort: Order,
    val proficientCategories: List<ProficientCategory>,
    val rangeCategories: List<RangeCategory>,
    val encumbranceCategories: List<EncumbranceCategory>
)
