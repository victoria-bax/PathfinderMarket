package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
internal class WeaponAbilitiesViewModel @Inject constructor(
    private val abilitiesInteractor: WeaponsAbilitiesInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<WeaponAbility>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<WeaponAbility>>> get() = _uiState


    fun loadAbilities(range: String) {
        if (_uiState.value is UIState.Success<*>)
            return
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                abilitiesInteractor.getAbilities(range).collect {
                    _uiState.value = UIState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }
}
