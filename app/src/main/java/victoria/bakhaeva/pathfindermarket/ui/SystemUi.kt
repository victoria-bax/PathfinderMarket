package victoria.bakhaeva.pathfindermarket.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun StatusBarAppearance(lightIcons: Boolean) {
    val context = LocalContext.current
    val view = LocalView.current
    SideEffect {
        val activity = context.findActivity() ?: return@SideEffect
        WindowInsetsControllerCompat(activity.window, view).isAppearanceLightStatusBars = lightIcons
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
