package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import timber.log.Timber
import victoria.bakhaeva.pathfindermarket.data.model.Weapon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponsSearchBar(
    allWeapons: List<Weapon>,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isActive by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(if (isActive) 1.0f else 0.7f),
    ) {
        Icon(
            Icons.Filled.Search, "",
            modifier = Modifier.align(Alignment.CenterEnd),
        )
        SearchBar(
            query = query,
            active = isActive,
            onQueryChange = {
                query = it
            },
            onActiveChange = {
                isActive = !isActive
            },
            onSearch = {
                query = it
                onSearch(it)
                isActive = false
            },
        ) {
            LazyColumn {
                val filtered =
                    allWeapons.filter { it.name?.contains(query, ignoreCase = true) == true }
                items(filtered.size) { weapon ->
                    val name = filtered[weapon].name ?: return@items
                    Text(
                        modifier = Modifier.clickable {
                            query = name
                            isActive = false
                            onSearch(name)
                        },
                        text = name
                    )
                }
            }
        }

    }
}

@Composable
@Preview
private fun previewWeaponsSearchBar() {
    WeaponsSearchBar(emptyList(), {})
}