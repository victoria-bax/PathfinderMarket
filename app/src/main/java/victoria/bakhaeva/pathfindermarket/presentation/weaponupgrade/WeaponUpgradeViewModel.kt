package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.mastercraftPrice
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import javax.inject.Inject

@HiltViewModel
internal class WeaponUpgradeViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeaponUpgradeState>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeaponUpgradeState>> get() = _uiState


    fun loadWeapon(alias: String) {
        if (_uiState.value is UIState.Success<*>)
            return
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getWeaponsInteractor.getWeapon(alias)?.let { weapon ->
                    _uiState.value = UIState.Success(
                        WeaponUpgradeState(
                            weapon = weapon,
                            isMasterwork = false,
                            sum = weapon.cost ?: 0.0,
                            abilities = emptyList()
                        )
                    )
                } ?: {
                    _uiState.value = UIState.Error(Exception("Weapon not found"))
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    fun onMasterworkChange(isMasterwork: Boolean) {
        updateState { state ->
            val copy = state.copy(
                isMasterwork = isMasterwork,
            )
            return@updateState copy.copy(
                sum = copy.countSum()
            )
        }
    }

    private fun WeaponUpgradeState.countSum(): Double =
        (weapon.cost ?: 0.0) +
                abilities.sumOf { it.moneyPrice ?: 0 } +
                if (isMasterwork) mastercraftPrice else 0.0


    private fun updateState(update: (WeaponUpgradeState) -> WeaponUpgradeState) {
        val data = (_uiState.value as? UIState.Success<WeaponUpgradeState>)?.data ?: return
        _uiState.value = UIState.Success(
            update(data)
        )
    }
}
