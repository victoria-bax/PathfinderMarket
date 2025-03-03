package victoria.bakhaeva.pathfindermarket.presentation.weapondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject

@HiltViewModel
internal class WeaponDetailViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Weapon>>(UIState.Loading)
    val uiState: StateFlow<UIState<Weapon>> get() = _uiState

    fun loadWeapon(alias: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getWeaponsInteractor.getWeapon(alias)?.let {
                    _uiState.value = UIState.Success(it)
                } ?: {
                    _uiState.value = UIState.Error(Exception("Weapon not found"))
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }

    }

}
