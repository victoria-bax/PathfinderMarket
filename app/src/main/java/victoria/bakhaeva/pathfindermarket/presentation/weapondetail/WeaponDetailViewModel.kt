package victoria.bakhaeva.pathfindermarket.presentation.weapondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import timber.log.Timber
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import javax.inject.Inject

@HiltViewModel
internal class WeaponDetailViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Weapon>>(UIState.Loading)
    val uiState: StateFlow<UIState<Weapon>> get() = _uiState

    init {
        val alias: String = checkNotNull(savedStateHandle["alias"])
        loadWeapon(alias)
    }

    private fun loadWeapon(alias: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getWeaponsInteractor.getWeapon(alias)?.let {
                    _uiState.value = UIState.Success(it)
                } ?: run {
                    _uiState.value = UIState.Error(R.string.error_weapon_not_found)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UIState.Error(R.string.error_generic)
            }
        }
    }
}
