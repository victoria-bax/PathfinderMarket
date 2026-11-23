package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListItemUiState
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

@Composable
fun WeaponList(
    state: WeaponListUiState,
    onSortSelected: (Order) -> Unit,
    onOpenFilter: () -> Unit,
    onSearch: (String) -> Unit,
    onOpenDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var openSortDialog by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchActive) {
        if (isSearchActive) focusRequester.requestFocus()
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Поиск",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            if (isSearchActive) {
                                isSearchActive = false
                                searchQuery = ""
                                onSearch("")
                            } else {
                                isSearchActive = true
                            }
                        }
                        .padding(8.dp),
                )
                AnimatedVisibility(
                    visible = isSearchActive,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally(),
                    modifier = Modifier.weight(1f),
                ) {
                    WeaponsSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = onSearch,
                        focusRequester = focusRequester,
                    )
                }
                AnimatedVisibility(visible = !isSearchActive) {
                    Row {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { openSortDialog = true }
                                .padding(8.dp),
                            painter = painterResource(state.sort.icon()),
                            contentDescription = ""
                        )
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { onOpenFilter() }
                                .padding(8.dp),
                            painter = painterResource(R.drawable.ic_filter),
                            contentDescription = ""
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            if (state.weapons.isEmpty()) {
                Box(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.search_empty),
                        style = typography.bodyLarge,
                        color = colorScheme.onBackground.copy(alpha = 0.5f),
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    items(state.weapons.size) { index ->
                        WeaponListItem(
                            weapon = state.weapons[index],
                            onWeaponCLick = { onOpenDetails(it.alias) },
                        )
                    }
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

@Composable
@Preview(heightDp = 700)
private fun PreviewWeaponListDark() {
    val weapons = WeaponPreviewProvider().values.toList()
    val allFilters = AllFilters(
        proficientCategories = weapons.map { it.proficientCategory }.distinctBy { it.alias },
        rangeCategories = weapons.map { it.rangeCategory }.distinctBy { it.alias },
        encumbranceCategories = weapons.mapNotNull { it.encumbranceCategory }.distinctBy { it.alias },
        books = weapons.mapNotNull { it.book }.distinctBy { it.alias },
    )
    val state = WeaponListUiState(
        weapons = weapons.map {
            WeaponListItemUiState(
                alias = it.alias,
                name = it.name,
                cost = it.cost,
                description = it.description,
                rangeIconRes = Range.from(it.rangeCategory.alias).icon,
                proficientCategoryName = it.proficientCategory.name,
                encumbranceCategoryName = it.encumbranceCategory?.name,
            )
        },
        sort = Order.DEFAULT,
        filterState = FilterState(
            allFilters = allFilters,
            selectedFilters = emptyList(),
        ),
    )
    PreviewDark {
        WeaponList(state = state, onSortSelected = {}, onOpenFilter = {}, onSearch = {}, onOpenDetails = {})
    }
}

@Composable
@Preview(heightDp = 700)
private fun PreviewWeaponListLight() {
    val weapons = WeaponPreviewProvider().values.toList()
    val allFilters = AllFilters(
        proficientCategories = weapons.map { it.proficientCategory }.distinctBy { it.alias },
        rangeCategories = weapons.map { it.rangeCategory }.distinctBy { it.alias },
        encumbranceCategories = weapons.mapNotNull { it.encumbranceCategory }.distinctBy { it.alias },
        books = weapons.mapNotNull { it.book }.distinctBy { it.alias },
    )
    val state = WeaponListUiState(
        weapons = weapons.map {
            WeaponListItemUiState(
                alias = it.alias,
                name = it.name,
                cost = it.cost,
                description = it.description,
                rangeIconRes = Range.from(it.rangeCategory.alias).icon,
                proficientCategoryName = it.proficientCategory.name,
                encumbranceCategoryName = it.encumbranceCategory?.name,
            )
        },
        sort = Order.DEFAULT,
        filterState = FilterState(
            allFilters = allFilters,
            selectedFilters = emptyList(),
        ),
    )
    PreviewLight {
        WeaponList(state = state, onSortSelected = {}, onOpenFilter = {}, onSearch = {}, onOpenDetails = {})
    }
}
