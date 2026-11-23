package victoria.bakhaeva.pathfindermarket.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkAmber,
    onPrimary = DawnInk,
    primaryContainer = NightSurface,
    onPrimaryContainer = DawnInk,
    secondary = DarkIceBlue,
    onSecondary = Color.White,
    secondaryContainer = DarkWood,
    onSecondaryContainer = DawnInk,
    tertiary = NightSteel,
    onTertiary = Color.White,
    tertiaryContainer = ShadowBrown,
    onTertiaryContainer = DawnInk,
    background = NightSky,
    onBackground = DawnInk,
    surface = NightSurface,
    onSurface = DawnInk,
    surfaceVariant = ShadowBrown,
    onSurfaceVariant = DawnInk,
    error = EmberCrimson,
    onError = Color.White,
    errorContainer = EmberCrimson,
    onErrorContainer = DawnInk,
    outline = DimAmber,
    outlineVariant = DarkWood,
)

private val LightColorScheme = lightColorScheme(
    primary = LanternAmber,
    onPrimary = WarmCloak,
    primaryContainer = SnowWhite,
    onPrimaryContainer = Ink,
    secondary = IceBlue,
    onSecondary = Color.White,
    secondaryContainer = LightCloak,
    onSecondaryContainer = Ink,
    tertiary = Steel,
    onTertiary = Color.White,
    tertiaryContainer = LightSteel,
    onTertiaryContainer = Ink,
    background = SnowWhite,
    onBackground = WarmCloak,
    surface = Parchment,
    onSurface = Ink,
    surfaceVariant = DarkParchment,
    onSurfaceVariant = Ink,
    error = Crimson,
    onError = Color.White,
    errorContainer = LightCrimson,
    onErrorContainer = Crimson,
    outline = WarmCloak,
    outlineVariant = LightCloak,
)

var colorScheme: ColorScheme = LightColorScheme

@Composable
fun PathfinderMarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.bodyMedium,
            content = content,
        )
    }
}
