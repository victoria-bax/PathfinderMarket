package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import victoria.bakhaeva.pathfindermarket.ui.upgrade.WeaponAbilitiesListScreen
import victoria.bakhaeva.pathfindermarket.ui.upgrade.WeaponAbilityDetailsScreen
import victoria.bakhaeva.pathfindermarket.ui.upgrade.WeaponUpgradeScreen
import victoria.bakhaeva.pathfindermarket.ui.weapons.WeaponDetailScreen
import victoria.bakhaeva.pathfindermarket.ui.weapons.WeaponFilterScreen
import victoria.bakhaeva.pathfindermarket.ui.home.HomeScreen
import victoria.bakhaeva.pathfindermarket.ui.home.SplashScreen
import victoria.bakhaeva.pathfindermarket.ui.weapons.WeaponListScreen

private const val ABILITY_KEY = "ability_key"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onFinished = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Home> {
            HomeScreen(
                onWeaponListClick = {
                    navController.navigate(Screen.WeaponList)
                }
            )
        }
        composable<Screen.WeaponList> {
            WeaponListScreen(
                onOpenDetails = {
                    navController.navigate(Screen.WeaponDetails(it))
                },
                onOpenFilter = {
                    navController.navigate(Screen.Filter)
                },
                modifier = Modifier,
            )
        }
        composable<Screen.Filter> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.WeaponList)
            }
            WeaponFilterScreen(
                parentEntry = parentEntry,
                onBack = { navController.popBackStack() },
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
            WeaponAbilitiesListScreen(
                screen.range,
                onBackClick = { navController.popBackStack() },
                onAbilitySelected = { ability ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        ABILITY_KEY, ability.alias
                    )
                    navController.popBackStack()
                },
                onAbilityDetails = {
                    navController.navigate(Screen.AbilityDetails(it.alias))
                }
            )
        }
        composable<Screen.AbilityDetails> { backStackEntry ->
            val screen: Screen.AbilityDetails = backStackEntry.toRoute()
            WeaponAbilityDetailsScreen(
                screen.alias,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

@Serializable
internal sealed class Screen {
    @Serializable
    object Splash : Screen()

    @Serializable
    object Home : Screen()

    @Serializable
    object WeaponList : Screen()

    @Serializable
    data class WeaponDetails(val alias: String) : Screen()

    @Serializable
    data class WeaponUpgrade(val alias: String) : Screen()

    @Serializable
    data class WeaponAbilities(val range: String) : Screen()

    @Serializable
    data class AbilityDetails(val alias: String) : Screen()

    @Serializable
    object Filter : Screen()
}
