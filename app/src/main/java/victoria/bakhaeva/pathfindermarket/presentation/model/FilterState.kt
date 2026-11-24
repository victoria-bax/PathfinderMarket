package victoria.bakhaeva.pathfindermarket.presentation.model

import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters

data class FilterState(
    val allFilters: AllFilters,
    val selectedFilters: List<String>,
)
