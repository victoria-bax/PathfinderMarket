package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState

@Composable
fun WeaponList(
    state: WeaponListUiState,
    onSortSelected: (Order) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var openSortDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .animateContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WeaponsSearchBar(
                    allWeapons = state.allWeapons,
                    onSearch = onSearch,
                )
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { openSortDialog = true }
                        .padding(8.dp),
                    painter = painterResource(state.sort.icon()),
                    contentDescription = ""
                )
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(state.weapons.size) { weapon ->
                    WeaponListItem(weapon = state.weapons[weapon])
                }
            }
            if (openSortDialog) {
                SortDialog(
                    selectedSort = state.sort,
                    onSortSelected = { sort ->
                        onSortSelected(sort)
                    },
                    onDismiss = {
                        openSortDialog = false
                    }
                )
            }
        },

        )
}

