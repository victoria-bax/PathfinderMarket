package victoria.bakhaeva.pathfindermarket.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

@Composable
internal fun ErrorScreen(
    @StringRes messageRes: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.surface)
            .padding(21.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(messageRes),
            color = colorScheme.onSurface,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(heightDp = 300)
@Composable
private fun PreviewErrorScreenDark() {
    PreviewDark {
        ErrorScreen(messageRes = R.string.error_generic)
    }
}

@Preview(heightDp = 300)
@Composable
private fun PreviewErrorScreenLight() {
    PreviewLight {
        ErrorScreen(messageRes = R.string.error_generic)
    }
}
