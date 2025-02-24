package victoria.bakhaeva.pathfindermarket.ui.sort

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.theme.LightYellow

@Composable
fun SortMenuItem(
    text: String,
    icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) LightYellow else lightColorScheme().background
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(icon),
            contentDescription = ""
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
        )
    }
}

@Composable
@Preview
private fun PreviewSortMenuItem() {
    SortMenuItem(
        text = "По возрастанию цены",
        icon = R.drawable.sort_descending,
        isSelected = true,
        onClick = {}
    )
}