package victoria.bakhaeva.pathfindermarket.ui.weapons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.domain.model.Order
import victoria.bakhaeva.pathfindermarket.presentation.model.SortUiState
import victoria.bakhaeva.pathfindermarket.ui.common.components.SortMenuItem
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SortDialog(
    sortUiState: SortUiState,
    onSortSelected: (Order) -> Unit,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        SortDialogContent(sortUiState, onSortSelected, onDismiss)
    }
}

@Composable
private fun SortDialogContent(
    sortUiState: SortUiState,
    onSortSelected: (Order) -> Unit,
    onDismiss: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            sortUiState.options.forEach { option ->
                SortMenuItem(
                    text = stringResource(option.labelRes),
                    icon = option.iconRes,
                    isSelected = option.order == sortUiState.selected,
                    onClick = {
                        onSortSelected(option.order)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSortDialogDark() {
    PreviewDark {
        SortDialogContent(sortUiState = defaultSortUiState, onSortSelected = {}, onDismiss = {})
    }
}

@Preview
@Composable
private fun PreviewSortDialogLight() {
    PreviewLight {
        SortDialogContent(sortUiState = defaultSortUiState, onSortSelected = {}, onDismiss = {})
    }
}
