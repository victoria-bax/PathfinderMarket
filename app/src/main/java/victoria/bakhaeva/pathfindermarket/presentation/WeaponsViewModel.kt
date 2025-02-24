package victoria.bakhaeva.pathfindermarket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponListState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import javax.inject.Inject

@HiltViewModel
internal class WeaponsViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeaponListState>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeaponListState>> get() = _uiState

    fun loadWeapons() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getWeaponsInteractor.execute().collect { weaponList ->
                    _uiState.value = UIState.Success(weaponList)
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    fun onSortSelected(sort: Order) {
        val data = (_uiState.value as? UIState.Success<WeaponListState>)?.data ?: return
        _uiState.value = UIState.Success(
            data.copy(
                weapons = data.weapons.sort(sort),
                sort = sort,
            )
        )
    }

    private fun List<Weapon>.sort(sort: Order): List<Weapon> = when (sort) {
        Order.PRICE_ASC -> sortedBy { it.cost }
        Order.PRICE_DESC -> sortedByDescending { it.cost }
        Order.NAME_ASC -> sortedBy { it.name }
        Order.NAME_DESC -> sortedByDescending { it.name }
    }

}
