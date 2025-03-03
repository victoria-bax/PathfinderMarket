package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import victoria.bakhaeva.pathfindermarket.ui.MarketFragment.Screen.WeaponAbilities.Companion.ABILITY_KEY
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponDetailScreen
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponUpgradeScreen
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponUpgradesListScreen

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
                    newAbilityAlias = navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                        ABILITY_KEY
                    ),
                    modifier = Modifier,
                    onBackClick = { navController.popBackStack() },
                    onAddAbilityClick = { range ->
                        navController.navigate(Screen.WeaponAbilities(range))
                    },
                )
            }

            composable<Screen.WeaponAbilities> { backStackEntry ->
                val screen: Screen.WeaponAbilities = backStackEntry.toRoute()
                WeaponUpgradesListScreen(
                    screen.range,
                    onBackClick = { navController.popBackStack() },
                    onAbilitySelected = { ability ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            ABILITY_KEY, ability.alias
                        )
                        navController.popBackStack()
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

        @Serializable
        data class WeaponAbilities(val range: String) : Screen() {
            companion object {
                const val ABILITY_KEY = "ability_key"
            }
        }
    }
}
