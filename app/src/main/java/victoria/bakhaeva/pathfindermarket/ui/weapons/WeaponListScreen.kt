package victoria.bakhaeva.pathfindermarket.ui.weapons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.presentation.weaponslist.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen
import victoria.bakhaeva.pathfindermarket.ui.common.StatusBarAppearance

@Composable
internal fun WeaponListScreen(
    viewModel: WeaponsViewModel = hiltViewModel(),
    onOpenDetails: (String) -> Unit,
    onOpenFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    StatusBarAppearance(lightIcons = true)
    Screen(
        state = viewModel.uiState.collectAsState().value,
        success = { state ->
            WeaponList(
                state = state,
                onSortSelected = viewModel::onSortSelected,
                onOpenFilter = onOpenFilter,
                onSearch = viewModel::onSearch,
                onOpenDetails = onOpenDetails,
                modifier = modifier,
            )
        }
    )
}
