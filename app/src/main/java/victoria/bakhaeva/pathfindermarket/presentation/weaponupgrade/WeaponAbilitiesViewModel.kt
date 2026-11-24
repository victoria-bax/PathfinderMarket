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
internal class WeaponAbilitiesViewModel @Inject constructor(
    private val abilitiesInteractor: WeaponsAbilitiesInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<WeaponAbility>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<WeaponAbility>>> get() = _uiState

    init {
        val range: String = checkNotNull(savedStateHandle["range"])
        loadAbilities(range)
    }

    private fun loadAbilities(range: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val abilities = abilitiesInteractor.getAbilities(range) ?: emptyList()
                _uiState.value = UIState.Success(abilities)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UIState.Error(R.string.error_generic)
            }
        }
    }
}
