package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import timber.log.Timber
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject

@HiltViewModel
internal class WeaponAbilityDetailsViewModel @Inject constructor(
    private val abilitiesInteractor: WeaponsAbilitiesInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeaponAbility>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeaponAbility>> get() = _uiState

    init {
        val alias: String = checkNotNull(savedStateHandle["alias"])
        loadAbility(alias)
    }

    private fun loadAbility(alias: String) {
        viewModelScope.launch {
            try {
                _uiState.value = abilitiesInteractor.getAbility(alias)?.let {
                    UIState.Success(it)
                } ?: UIState.Error(R.string.error_ability_not_found)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UIState.Error(R.string.error_generic)
            }
        }
    }
}
