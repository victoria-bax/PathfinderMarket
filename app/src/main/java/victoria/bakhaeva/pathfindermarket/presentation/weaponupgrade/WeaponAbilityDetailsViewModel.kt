package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject

@HiltViewModel
internal class WeaponAbilityDetailsViewModel @Inject constructor(
    private val abilitiesInteractor: WeaponsAbilitiesInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeaponAbility>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeaponAbility>> get() = _uiState

    fun loadAbility(alias: String) {
        viewModelScope.launch {
            _uiState.value = abilitiesInteractor.getAbility(alias)?.let {
                UIState.Success(it)
            } ?: UIState.Error(NullPointerException("Свойство не найдено"))
        }
    }
}
