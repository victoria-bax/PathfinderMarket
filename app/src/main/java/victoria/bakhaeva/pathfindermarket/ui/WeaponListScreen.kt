package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.weaponslist.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.weaponList.WeaponList

@Composable
internal fun WeaponListScreen(
    viewModel: WeaponsViewModel = hiltViewModel(),
    onOpenDetails: (Weapon) -> Unit,
    modifier: Modifier = Modifier,
) = Screen (
    state = viewModel.uiState.collectAsState().value,
    success = { state ->
        WeaponList(
            state = state,
            onSortSelected = viewModel::onSortSelected,
            onFilterChecked = viewModel::onFilterChecked,
            onSearch = viewModel::onSearch,
            onOpenDetails = onOpenDetails,
            modifier = modifier,
        )
    }
)