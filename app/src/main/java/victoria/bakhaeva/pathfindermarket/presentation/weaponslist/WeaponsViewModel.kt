package victoria.bakhaeva.pathfindermarket.presentation.weaponslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.FilterPreferencesRepository
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.mapper.WeaponUiStateMapper
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import javax.inject.Inject

@HiltViewModel
internal class WeaponsViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor,
    private val weaponUiStateMapper: WeaponUiStateMapper,
    private val filterPrefs: FilterPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeaponListUiState>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeaponListUiState>> get() = _uiState

    init {
        loadWeapons()
    }

    fun loadWeapons() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val savedFilters = filterPrefs.loadSelectedFilters()
                getWeaponsInteractor.getWeapons().collect { weaponList ->
                    val state = weaponUiStateMapper.map(weaponList)
                    _uiState.value = UIState.Success(
                        if (savedFilters.isEmpty()) state else applyFilters(state, savedFilters)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    suspend fun getWeapon(alias: String): Weapon? = getWeaponsInteractor.getWeapon(alias)

    fun onSortSelected(sort: Order) =
        updateWeapons {
            it.copy(
                weapons = it.weapons.sort(sort),
                sort = sort,
            )
        }

    private fun List<Weapon>.sort(sort: Order): List<Weapon> = when (sort) {
        Order.PRICE_ASC -> sortedBy { it.cost }
        Order.PRICE_DESC -> sortedByDescending { it.cost }
        Order.NAME_ASC -> sortedBy { it.name }
        Order.NAME_DESC -> sortedByDescending { it.name }
    }

    fun onSearch(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            updateWeapons { it.copy(weapons = it.allWeapons.sort(it.sort)) }
            return
        }
        viewModelScope.launch {
            val results = getWeaponsInteractor.searchWeapons(searchQuery)
            updateWeapons { it.copy(weapons = results.sort(it.sort)) }
        }
    }

    private fun updateWeapons(update: (WeaponListUiState) -> WeaponListUiState) {
        val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return
        _uiState.value = UIState.Success(update(data))
    }

    fun onFilterChecked(aliases: List<String>, checked: Boolean) {
        updateWeapons {
            val selectedFilters = if (checked)
                it.filterState.selectedFilters + aliases
            else
                it.filterState.selectedFilters - aliases
            it.copy(
                weapons = it.allWeapons.filter { weapon ->
                    selectedFilters.contains(weapon.proficientCategory.alias)
                            && selectedFilters.contains(weapon.rangeCategory.alias)
                            && weapon.encumbranceCategory?.alias?.let { alias ->
                        selectedFilters.contains(alias)
                    } ?: true
                },
                filterState = it.filterState.copy(selectedFilters = selectedFilters)
            )
        }
    }

    fun onFilterApplied(selectedAliases: List<String>) {
        viewModelScope.launch {
            filterPrefs.saveSelectedFilters(selectedAliases)
        }
        updateWeapons { applyFilters(it, selectedAliases) }
    }

    private fun applyFilters(state: WeaponListUiState, selectedAliases: List<String>): WeaponListUiState {
        val allFilters = state.filterState.allFilters
        val selectedSet = selectedAliases.toSet()

        val activeRange = selectedSet.intersect(allFilters.rangeCategories.map { it.alias }.toSet())
        val activeProficient = selectedSet.intersect(allFilters.proficientCategories.map { it.alias }.toSet())
        val activeEncumbrance = selectedSet.intersect(allFilters.encumbranceCategories.map { it.alias }.toSet())
        val activeBooks = selectedSet.intersect(allFilters.books.map { it.alias }.toSet())

        return state.copy(
            weapons = state.allWeapons.filter { weapon ->
                (activeRange.isEmpty() || weapon.rangeCategory.alias in activeRange)
                        && (activeProficient.isEmpty() || weapon.proficientCategory.alias in activeProficient)
                        && (activeEncumbrance.isEmpty() || weapon.encumbranceCategory?.alias in activeEncumbrance)
                        && (activeBooks.isEmpty() || weapon.book?.alias in activeBooks)
            },
            filterState = state.filterState.copy(selectedFilters = selectedAliases)
        )
    }
}
