package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.Provides
import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.ui.sort.SortMenuItem
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.AllFilters
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    state: FilterState,
    onFilterChecked: (String, Boolean) -> Unit,
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
                            Text("Дистанция", style = MaterialTheme.typography.headlineSmall)
                        }
                        items(rangeCategories.size) { index ->
                            val rangeCategory = rangeCategories[index]
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.contains(rangeCategory.alias),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(rangeCategory.alias, checked)
                                    }
                                )
                                Text(rangeCategory.name)
                            }
                        }
                    }
                state.allFilters.proficientCategories.takeIf { it.isNotEmpty() }
                    ?.let { proficientCategories ->
                        item {
                            Text("Тип", style = MaterialTheme.typography.headlineSmall)
                        }
                        items(proficientCategories.size) { index ->
                            val proficientCategory = proficientCategories[index]
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.contains(proficientCategory.alias),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(proficientCategory.alias, checked)
                                    }
                                )
                                Text(proficientCategory.name)
                            }
                        }
                    }
                state.allFilters.encumbranceCategories.takeIf { it.isNotEmpty() }
                    ?.let { encumbranceCategories ->
                        item {
                            Text("Количество рук", style = MaterialTheme.typography.headlineSmall)
                        }
                        items(encumbranceCategories.size) { index ->
                            val encumbranceCategory = encumbranceCategories[index]
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = state.selectedFilters.contains(encumbranceCategory.alias),
                                    onCheckedChange = { checked ->
                                        onFilterChecked(encumbranceCategory.alias, checked)
                                    }
                                )
                                Text(encumbranceCategory.name)
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
                proficientCategories = listOf(ProficientCategory("Боеприпас", "ammo")),
                rangeCategories = listOf(RangeCategory("Ближнего боя", "melee")),
                encumbranceCategories = listOf(EncumbranceCategory("Полуторное", "oneHanded")),
            ),
            selectedFilters = listOf("ammo")
        ),
        { _, _ -> },
        {},
    )
}