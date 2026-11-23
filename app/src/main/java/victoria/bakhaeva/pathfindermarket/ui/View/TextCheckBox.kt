package victoria.bakhaeva.pathfindermarket.ui.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

@Composable
fun TextCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .clickable {
                onCheckedChange(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier.clickable(enabled = false) {},
            checked = isChecked,
            onCheckedChange = { },
            enabled = enabled,
        )
        Text(
            text = text,
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Composable
private fun PreviewTextCheckBoxDark() {
    PreviewDark {
        TextCheckBox(isChecked = true, onCheckedChange = {}, text = "Мастерское оружие")
    }
}

@Preview
@Composable
private fun PreviewTextCheckBoxLight() {
    PreviewLight {
        TextCheckBox(isChecked = false, onCheckedChange = {}, text = "Мастерское оружие")
    }
}
