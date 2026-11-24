package victoria.bakhaeva.pathfindermarket.ui.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun StatusBarAppearance(lightIcons: Boolean) {
    val context = LocalContext.current
    val view = LocalView.current
    val navigationBarColor = MaterialTheme.colorScheme.background.toArgb()
    val isLightTheme = lightIcons
    SideEffect {
        val activity = context.findActivity() ?: return@SideEffect
        val controller = WindowInsetsControllerCompat(activity.window, view)
        controller.isAppearanceLightStatusBars = lightIcons
        controller.isAppearanceLightNavigationBars = isLightTheme
        activity.window.navigationBarColor = navigationBarColor
    }
}

private fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
