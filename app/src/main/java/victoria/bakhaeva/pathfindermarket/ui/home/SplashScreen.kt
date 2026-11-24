package victoria.bakhaeva.pathfindermarket.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import kotlinx.coroutines.delay
import victoria.bakhaeva.pathfindermarket.ui.theme.PathfinderMarketTheme
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.common.StatusBarAppearance
import victoria.bakhaeva.pathfindermarket.ui.theme.InkLight
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    StatusBarAppearance(lightIcons = false)
    LaunchedEffect(Unit) {
        delay(3000.milliseconds)
        onFinished()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = stringResource(R.string.home_title),
            modifier = Modifier
                .padding(64.dp),
            style = Typography.titleLarge.copy(
                color = InkLight,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    blurRadius = 32f,
                ),
            ),
        )
    }
}

@Preview(heightDp = 700)
@Composable
private fun PreviewSplashScreenDark() {
    PreviewDark { SplashScreen(onFinished = {}) }
}