package victoria.bakhaeva.pathfindermarket.presentation.weaponslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
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
                getWeaponsInteractor.getWeapons().collect { weaponList ->
                    _uiState.value = UIState.Success(weaponUiStateMapper.map(weaponList))
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    fun getWeapon(alias: String): Weapon? = getWeaponsInteractor.getWeapon(alias)

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

    fun onSearch(searchQuery: String) = updateWeapons {
        it.copy(
            weapons = it.allWeapons.filter { weapon ->
                weapon.name.contains(searchQuery, ignoreCase = true)
            }.sort(it.sort)
        )
    }

    private fun updateWeapons(update: (WeaponListUiState) -> WeaponListUiState) {
        val data = (_uiState.value as? UIState.Success<WeaponListUiState>)?.data ?: return
        _uiState.value = UIState.Success(
            update(data)
        )
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
                filterState = it.filterState.copy(
                    selectedFilters = selectedFilters
                )
            )
        }
    }

}
