package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import victoria.bakhaeva.pathfindermarket.presentation.UIState

@Composable
fun <T> Screen(
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