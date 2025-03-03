package victoria.bakhaeva.pathfindermarket.ui

import android.os.Bundle
import android.view.View
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import victoria.bakhaeva.pathfindermarket.presentation.weaponslist.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponDetailScreen
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponUpgradeScreen

@AndroidEntryPoint
class MarketFragment : BaseComposeFragment() {

    @Composable
    override fun ScreenContent() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.WeaponList,
        ) {
            composable<Screen.WeaponList> {
                WeaponListScreen(
                    onOpenDetails = {
                        navController.navigate(Screen.WeaponDetails(it.alias))
                    },
                    modifier = Modifier,
                )
            }
            composable<Screen.WeaponDetails> { backStackEntry ->
                val screen: Screen.WeaponDetails = backStackEntry.toRoute()
                WeaponDetailScreen(
                    screen.alias,
                    onBackClick = { navController.popBackStack() },
                    onUpgradeClick = {
                        navController.navigate(Screen.WeaponUpgrade(screen.alias))
                    },
                    modifier = Modifier,
                )
            }
            composable<Screen.WeaponUpgrade> { backStackEntry ->
                val screen: Screen.WeaponUpgrade = backStackEntry.toRoute()
                WeaponUpgradeScreen(
                    screen.alias,
                    onBackClick = { navController.popBackStack() },
                    onAddAbilityClick = {
                        // todo ability list screen
                    },
                )
            }
        }


    }

    @Serializable
    private sealed class Screen {
        @Serializable
        object WeaponList : Screen()

        @Serializable
        data class WeaponDetails(val alias: String) : Screen()

        @Serializable
        data class WeaponUpgrade(val alias: String) : Screen()
    }
}
