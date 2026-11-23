package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    state: FilterState,
    onApply: (selected: List<String>) -> Unit,
    onBack: () -> Unit,
) {
    var selected by remember(state.selectedFilters) { mutableStateOf(state.selectedFilters.toSet()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .statusBarsPadding()
    ) {
        // top bar
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Text(
                text = stringResource(R.string.filter_title),
                style = typography.titleLarge,
                modifier = Modifier.weight(1f).padding(start = 4.dp),
            )
            TextButton(onClick = { selected = emptySet() }) {
                Text(
                    text = stringResource(R.string.filter_clear_all),
                    color = colorScheme.primary,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            val allFilters = state.allFilters

            if (allFilters.rangeCategories.isNotEmpty()) {
                FilterSection(title = stringResource(R.string.filter_range_category)) {
                    allFilters.rangeCategories.forEach { item ->
                        FilterChipItem(
                            label = item.name ?: item.alias,
                            selected = item.alias in selected,
                            onToggle = { selected = selected.toggle(item.alias) },
                        )
                    }
                }
            }

            if (allFilters.proficientCategories.isNotEmpty()) {
                FilterSection(title = stringResource(R.string.filter_proficient_type)) {
                    allFilters.proficientCategories.forEach { item ->
                        FilterChipItem(
                            label = item.name ?: item.alias,
                            selected = item.alias in selected,
                            onToggle = { selected = selected.toggle(item.alias) },
                        )
                    }
                }
            }

            if (allFilters.encumbranceCategories.isNotEmpty()) {
                FilterSection(title = stringResource(R.string.filter_hands_count)) {
                    allFilters.encumbranceCategories.forEach { item ->
                        FilterChipItem(
                            label = item.name ?: item.alias,
                            selected = item.alias in selected,
                            onToggle = { selected = selected.toggle(item.alias) },
                        )
                    }
                }
            }

            if (allFilters.books.isNotEmpty()) {
                FilterSection(title = stringResource(R.string.filter_book)) {
                    allFilters.books.forEach { book ->
                        FilterChipItem(
                            label = book.name,
                            selected = book.alias in selected,
                            onToggle = { selected = selected.toggle(book.alias) },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { onApply(selected.toList()) },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            shape = RoundedCornerShape(50),
        ) {
            Text(
                text = stringResource(R.string.filter_apply),
                style = typography.titleMedium,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterSection(
    title: String,
    content: @Composable FlowRowScope.() -> Unit,
) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(text = title, style = typography.titleMedium, color = colorScheme.onBackground)
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = content,
    )
}

@Composable
private fun FilterChipItem(
    label: String,
    selected: Boolean,
    onToggle: () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = onToggle,
        label = { Text(label.uppercase(), style = typography.labelMedium) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = colorScheme.primary,
            selectedLabelColor = colorScheme.onPrimary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = colorScheme.outline,
            selectedBorderColor = colorScheme.primary,
        ),
    )
}

private fun Set<String>.toggle(alias: String) =
    if (alias in this) this - alias else this + alias
