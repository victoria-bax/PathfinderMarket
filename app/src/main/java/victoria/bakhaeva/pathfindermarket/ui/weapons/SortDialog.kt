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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.ui.common.components.SortMenuItem
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    selectedSort: Order,
    onSortSelected: (Order) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        SortDialogContent(selectedSort, onSortSelected, onDismiss)
    }
}

@Composable
private fun SortDialogContent(
    selectedSort: Order,
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
            Order.entries.forEach {
                SortMenuItem(
                    text = stringResource(it.labelRes),
                    icon = it.icon(),
                    isSelected = it == selectedSort,
                    onClick = {
                        onSortSelected(it)
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
        SortDialogContent(selectedSort = Order.DEFAULT, onSortSelected = {}, onDismiss = {})
    }
}

@Preview
@Composable
private fun PreviewSortDialogLight() {
    PreviewLight {
        SortDialogContent(selectedSort = Order.DEFAULT, onSortSelected = {}, onDismiss = {})
    }
}
