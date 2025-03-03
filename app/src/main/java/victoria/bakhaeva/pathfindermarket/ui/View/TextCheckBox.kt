package victoria.bakhaeva.pathfindermarket.ui.View

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TextCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
        )
        Text(
            text = text,
            style = textStyle,
        )
    }
}