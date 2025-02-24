package victoria.bakhaeva.pathfindermarket.ui.weaponList

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
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.ui.sort.SortMenuItem

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
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Order.entries.forEach {
                    SortMenuItem(
                        text = it.value,
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
}