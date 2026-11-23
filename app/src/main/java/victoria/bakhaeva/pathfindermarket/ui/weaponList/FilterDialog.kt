package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.ui.View.TextCheckBox
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    state: FilterState,
    onFilterChecked: (List<String>, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                state.allFilters.rangeCategories.takeIf { it.isNotEmpty() }
                    ?.let { rangeCategories ->
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.containsAll(rangeCategories.map { it.alias.value }),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(rangeCategories.map { it.alias.value }, checked)
                                    }
                                )
                                Text(stringResource(R.string.filter_range_category), style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                        items(rangeCategories.size) { index ->
                            val rangeCategory = rangeCategories[index]
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.contains(rangeCategory.alias.value),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(listOf(rangeCategory.alias.value), checked)
                                    }
                                )
                                rangeCategory.name?.let { Text(it) }
                            }
                        }
                    }
                state.allFilters.proficientCategories.takeIf { it.isNotEmpty() }
                    ?.let { proficientCategories ->
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.containsAll(proficientCategories.map { it.alias.value }),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(
                                            proficientCategories.map { it.alias.value },
                                            checked
                                        )
                                    }
                                )
                                Text(stringResource(R.string.filter_proficient_type), style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                        items(proficientCategories.size) { index ->
                            val proficientCategory = proficientCategories[index]
                            TextCheckBox(
                                isChecked = state.selectedFilters.contains(proficientCategory.alias.value),
                                onCheckedChange = { checked ->
                                    onFilterChecked(listOf(proficientCategory.alias.value), checked)
                                },
                                text = proficientCategory.name ?: ""
                            )
                        }
                    }
                state.allFilters.encumbranceCategories.takeIf { it.isNotEmpty() }
                    ?.let { encumbranceCategories ->
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.containsAll(
                                        encumbranceCategories.map { it.alias.value }),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(
                                            encumbranceCategories.map { it.alias.value },
                                            checked
                                        )
                                    }
                                )
                                Text(stringResource(R.string.filter_hands_count), style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                        items(encumbranceCategories.size) { index ->
                            val encumbranceCategory = encumbranceCategories[index]
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.contains(encumbranceCategory.alias.value),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(listOf(encumbranceCategory.alias.value), checked)
                                    }
                                )
                                encumbranceCategory.name?.let { Text(it) }
                            }
                        }
                    }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFilterDialog() {
    FilterDialog(
        FilterState(
            allFilters = AllFilters(
                proficientCategories = listOf(ProficientCategory("Боеприпас", ProficientAlias.AMMUNITION)),
                rangeCategories = listOf(RangeCategory("Ближнего боя", RangeAlias.MELEE)),
                encumbranceCategories = listOf(EncumbranceCategory("Одноручное", EncumbranceAlias.ONE_HANDED)),
                books = emptyList(),
            ),
            selectedFilters = listOf(ProficientAlias.AMMUNITION.value)
        ),
        { _, _ -> },
        {},
    )
}
