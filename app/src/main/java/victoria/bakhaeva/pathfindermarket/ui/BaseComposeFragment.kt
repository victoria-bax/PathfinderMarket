package victoria.bakhaeva.pathfindermarket.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import victoria.bakhaeva.pathfindermarket.ui.theme.PathfinderMarketTheme

abstract class BaseComposeFragment : Fragment() {
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = createComposeView { ScreenContent() }

    @Composable
    abstract fun ScreenContent()

    fun createComposeView(
        content: @Composable () -> Unit,
    ): ComposeView =
        ComposeView(requireContext())
            .apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                )

                setContent {
                    PathfinderMarketTheme {
                        content()
                    }
                }
            }
}