package victoria.bakhaeva.pathfindermarket.ui.weapons.state

import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters

data class FilterState(
    val allFilters: AllFilters,
    val selectedFilters: List<String>,
)
