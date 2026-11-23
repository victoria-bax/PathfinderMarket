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
    primary = DarkGold,
    onPrimary = DarkBackground,
    primaryContainer = DarkYellow,
    onPrimaryContainer = DarkInk,
    secondary = DarkGrassGreen,
    onSecondary = Color.White,
    secondaryContainer = DarkBrown,
    onSecondaryContainer = DarkInk,
    tertiary = DarkSteel,
    onTertiary = Color.White,
    tertiaryContainer = DarkParchmentColor,
    onTertiaryContainer = DarkInk,
    background = DarkBackground,
    onBackground = DarkInk,
    surface = DarkSurface,
    onSurface = DarkInk,
    surfaceVariant = DarkParchmentColor,
    onSurfaceVariant = DarkInk,
    error = DarkCrimson,
    onError = Color.White,
    errorContainer = DarkCrimson,
    onErrorContainer = DarkInk,
    outline = DarkYellow,
    outlineVariant = DarkBrown,
)

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    onPrimary = Brown,
    primaryContainer = LightYellow,
    onPrimaryContainer = Ink,
    secondary = GrassGreen,
    onSecondary = Color.White,
    secondaryContainer = LightBrown,
    onSecondaryContainer = Ink,
    tertiary = Steel,
    onTertiary = Color.White,
    tertiaryContainer = LightSteel,
    onTertiaryContainer = Ink,
    background = LightYellow,
    onBackground = Brown,
    surface = Parchment,
    onSurface = Ink,
    surfaceVariant = DarkParchment,
    onSurfaceVariant = Ink,
    error = Crimson,
    onError = Color.White,
    errorContainer = LightCrimson,
    onErrorContainer = Crimson,
    outline = Brown,
    outlineVariant = LightBrown,
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