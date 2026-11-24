package victoria.bakhaeva.pathfindermarket.presentation

import androidx.annotation.StringRes

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(@StringRes val messageRes: Int) : UIState<Nothing>()
}
