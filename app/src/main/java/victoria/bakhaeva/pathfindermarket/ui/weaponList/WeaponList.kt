package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponList(
    state: WeaponListState,
    onSortSelected: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    var openSortDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Weapons")
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp),
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp)
                                .clickable { openSortDialog = true },
                            painter = painterResource(state.sort.icon()),
                            contentDescription = ""
                        )
                    }
                },
            )
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

