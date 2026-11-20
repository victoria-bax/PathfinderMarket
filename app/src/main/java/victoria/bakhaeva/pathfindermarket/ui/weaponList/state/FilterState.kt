package victoria.bakhaeva.pathfindermarket.ui.weaponList.state

import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory

data class FilterState(
    val allFilters: AllFilters,
    val selectedFilters: List<String>,
)

data class AllFilters(
    val proficientCategories: List<ProficientCategory>,
    val rangeCategories: List<RangeCategory>,
    val encumbranceCategories: List<EncumbranceCategory>,
)
