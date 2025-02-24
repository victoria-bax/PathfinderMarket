package victoria.bakhaeva.pathfindermarket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import javax.inject.Inject

@HiltViewModel
internal class WeaponsViewModel @Inject constructor(
    private val getWeaponsInteractor: WeaponsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Weapon>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Weapon>>> get() = _uiState

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
}
