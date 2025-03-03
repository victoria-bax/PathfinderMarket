package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
internal class WeaponUpgradeViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor,
    private val abilitiesInteractor: WeaponsAbilitiesInteractor,
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
            state.copy(
                isMasterwork = isMasterwork,
            )
        }
    }

    private fun updateState(update: (WeaponUpgradeState) -> WeaponUpgradeState) {
        val data = (_uiState.value as? UIState.Success<WeaponUpgradeState>)?.data ?: return
        _uiState.value = UIState.Success(
            update(data)
        )
    }

    fun addAbility(abilityAlias: String) {
        val data = (_uiState.value as? UIState.Success<WeaponUpgradeState>)?.data ?: return
        if (data.abilities.none { it.alias == abilityAlias }) {
            _uiState.value = UIState.Success(
                data.copy(
                    abilities = data.abilities + listOfNotNull(
                        abilitiesInteractor.getAbility(
                            abilityAlias
                        )
                    )
                )
            )
        }
    }
}
