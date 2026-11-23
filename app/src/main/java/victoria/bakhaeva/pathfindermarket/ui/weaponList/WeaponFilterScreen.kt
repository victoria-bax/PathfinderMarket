package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.weaponslist.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.StatusBarAppearance

@Composable
fun WeaponFilterScreen(
    parentEntry: NavBackStackEntry,
    onBack: () -> Unit,
) {
    val viewModel: WeaponsViewModel = hiltViewModel(parentEntry)
    val uiState = viewModel.uiState.collectAsState().value
    if (uiState !is UIState.Success) return

    StatusBarAppearance(lightIcons = true)

    FilterScreen(
        state = uiState.data.filterState,
        onApply = { selected ->
            viewModel.onFilterApplied(selected)
            onBack()
        },
        onBack = onBack,
    )
}
