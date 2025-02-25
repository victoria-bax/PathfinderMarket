package victoria.bakhaeva.pathfindermarket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
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

    fun loadWeapons() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getWeaponsInteractor.execute().collect { weaponList ->
                    _uiState.value = UIState.Success(weaponUiStateMapper.map(weaponList))
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

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

}
