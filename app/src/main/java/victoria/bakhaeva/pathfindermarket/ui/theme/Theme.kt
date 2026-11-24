package victoria.bakhaeva.pathfindermarket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AmberDark,
    onPrimary = NightBg,
    primaryContainer = NightVariant,
    onPrimaryContainer = InkLight,
    secondary = SteelDark,
    onSecondary = NightBg,
    secondaryContainer = WoodDark,
    onSecondaryContainer = InkLight,
    tertiary = SteelDark,
    onTertiary = NightBg,
    tertiaryContainer = NightVariant,
    onTertiaryContainer = InkLight,
    background = NightBg,
    onBackground = InkLight,
    surface = NightSurface,
    onSurface = InkLight,
    surfaceVariant = NightVariant,
    onSurfaceVariant = InkLight,
    error = EmberDark,
    onError = Color.White,
    errorContainer = EmberDark,
    onErrorContainer = InkLight,
    outline = AmberDark,
    outlineVariant = WoodDark,
)

private val LightColorScheme = lightColorScheme(
    primary = AmberLight,
    onPrimary = Color.White,
    primaryContainer = ParchmentSurface,
    onPrimaryContainer = InkDark,
    secondary = SteelLight,
    onSecondary = Color.White,
    secondaryContainer = ParchmentVariant,
    onSecondaryContainer = InkDark,
    tertiary = SteelLight,
    onTertiary = Color.White,
    tertiaryContainer = ParchmentVariant,
    onTertiaryContainer = InkDark,
    background = ParchmentBg,
    onBackground = InkDark,
    surface = ParchmentSurface,
    onSurface = InkDark,
    surfaceVariant = ParchmentVariant,
    onSurfaceVariant = InkDark,
    error = EmberLight,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = EmberLight,
    outline = WoodLight,
    outlineVariant = ParchmentVariant,
)

@Composable
fun PathfinderMarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

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

@Composable
fun PreviewDark(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColorScheme, typography = Typography) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
            Surface(color = DarkColorScheme.background, content = content)
        }
    }
}

@Composable
fun PreviewLight(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColorScheme, typography = Typography) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
            Surface(color = LightColorScheme.background, content = content)
        }
    }
}

@Composable
internal fun PathfinderMarketPreview(
    content: @Composable () -> Unit
) {
    Column {
        PreviewDark(content)
        PreviewLight(content)
    }
}
