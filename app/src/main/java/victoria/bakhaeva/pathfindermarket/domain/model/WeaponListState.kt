package victoria.bakhaeva.pathfindermarket.domain.model

import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order

data class WeaponListState(
    val weapons: List<Weapon> = emptyList(),
    val sort: Order = Order.DEFAULT,
    val proficientCategories: List<ProficientCategory>,
    val rangeCategories: List<RangeCategory>,
    val encumbranceCategories: List<EncumbranceCategory>
)
