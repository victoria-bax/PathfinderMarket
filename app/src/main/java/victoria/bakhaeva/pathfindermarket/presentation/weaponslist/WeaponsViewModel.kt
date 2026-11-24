package victoria.bakhaeva.pathfindermarket.presentation.weaponslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.mapper.WeaponUiStateMapper
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListItemUiState
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.domain.model.Order
import victoria.bakhaeva.pathfindermarket.presentation.model.SortUiState
import javax.inject.Inject

@HiltViewModel
internal class WeaponsViewModel @Inject constructor(
    private val interactor: WeaponsInteractor,
    private val mapper: WeaponUiStateMapper,
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
                val savedFilters = interactor.loadFilters()
                interactor.getWeapons().collect { weaponList ->
                    val base = mapper.map(weaponList)
                    _uiState.value = UIState.Success(
                        if (savedFilters.isEmpty()) base
                        else base.withFilters(savedFilters)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    fun onSortSelected(sort: Order) = updateWeapons { state ->
        val sorted = when (sort) {
            Order.PRICE_ASC -> state.weapons.sortedBy { it.cost ?: Int.MAX_VALUE }
            Order.PRICE_DESC -> state.weapons.sortedByDescending { it.cost ?: Int.MIN_VALUE }
            Order.NAME_ASC -> state.weapons.sortedBy { it.name }
            Order.NAME_DESC -> state.weapons.sortedByDescending { it.name }
        }
        state.copy(weapons = sorted, sort = state.sort.copy(selected = sort))
    }

    fun onSearch(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            viewModelScope.launch { reapplyCurrentFilters() }
            return
        }
        viewModelScope.launch {
            val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return@launch
            val results = interactor.searchWeapons(searchQuery, data.sort.selected)
            updateWeapons { it.copy(weapons = with(mapper) { results.toUiStates() }) }
        }
    }

    fun onFilterApplied(selectedAliases: List<String>) {
        viewModelScope.launch {
            interactor.saveFilters(selectedAliases)
            val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return@launch
            _uiState.value = UIState.Success(data.withFilters(selectedAliases))
        }
    }

    private suspend fun reapplyCurrentFilters() {
        val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return
        _uiState.value = UIState.Success(data.withFilters(data.filterState.selectedFilters))
    }

    private suspend fun WeaponListUiState.withFilters(selectedAliases: List<String>): WeaponListUiState {
        val filtered = interactor.filterWeapons(filterState.allFilters, selectedAliases, sort.selected)
        return copy(
            weapons = with(mapper) { filtered.toUiStates() },
            filterState = filterState.copy(selectedFilters = selectedAliases),
        )
    }

    private fun updateWeapons(update: (WeaponListUiState) -> WeaponListUiState) {
        val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return
        _uiState.value = UIState.Success(update(data))
    }
}
