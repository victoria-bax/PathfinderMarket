package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.LoadingScreen
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import victoria.bakhaeva.pathfindermarket.ui.weaponList.WeaponList

@Composable
internal fun WeaponListScreen(
    state: UIState<WeaponListUiState>,
    onSortSelected: (Order) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is UIState.Loading -> LoadingScreen(modifier)

        is UIState.Success -> {
            WeaponList(
                state = state.data,
                onSortSelected = onSortSelected,
                onSearch = onSearch,
                modifier = modifier,
            )
        }

        is UIState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка: ${state.exception.message}")
            }
        }
    }
}


