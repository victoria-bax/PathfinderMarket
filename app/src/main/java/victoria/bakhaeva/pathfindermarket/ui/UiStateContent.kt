package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.ui.common.ErrorScreen
import victoria.bakhaeva.pathfindermarket.ui.common.LoadingScreen

@Composable
internal fun <T> UiStateContent(
    state: UIState<T>,
    success: @Composable (data: T) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is UIState.Loading -> LoadingScreen(modifier)
        is UIState.Success -> success(state.data)
        is UIState.Error -> ErrorScreen(state.exception)
    }
}
