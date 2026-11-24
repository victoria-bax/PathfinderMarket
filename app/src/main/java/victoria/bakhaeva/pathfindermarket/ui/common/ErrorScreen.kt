package victoria.bakhaeva.pathfindermarket.ui.common

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
fun ErrorScreen(
    exception: Throwable,
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
            text = stringResource(R.string.error_message, exception.message ?: ""),
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
        ErrorScreen(exception = Exception("Не удалось загрузить данные"))
    }
}

@Preview(heightDp = 300)
@Composable
private fun PreviewErrorScreenLight() {
    PreviewLight {
        ErrorScreen(exception = Exception("Не удалось загрузить данные"))
    }
}
