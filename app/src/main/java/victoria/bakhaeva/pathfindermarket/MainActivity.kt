package victoria.bakhaeva.pathfindermarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import victoria.bakhaeva.pathfindermarket.ui.AppNavHost
import victoria.bakhaeva.pathfindermarket.ui.theme.PathfinderMarketTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PathfinderMarketTheme {
                AppNavHost()
            }
        }
    }
}
