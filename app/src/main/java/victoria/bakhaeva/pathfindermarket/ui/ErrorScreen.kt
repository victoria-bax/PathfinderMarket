package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import victoria.bakhaeva.pathfindermarket.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.lang.Exception

@Composable
fun ErrorScreen(
    exception: Throwable,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.error_message, exception.message ?: ""))
    }
}