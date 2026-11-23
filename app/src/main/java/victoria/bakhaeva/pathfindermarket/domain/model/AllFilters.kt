package victoria.bakhaeva.pathfindermarket.domain.model

data class AllFilters(
    val proficientCategories: List<ProficientCategory>,
    val rangeCategories: List<RangeCategory>,
    val encumbranceCategories: List<EncumbranceCategory>,
    val books: List<Book>,
)
