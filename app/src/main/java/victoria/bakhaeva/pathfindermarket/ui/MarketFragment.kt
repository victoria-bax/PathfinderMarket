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
import victoria.bakhaeva.pathfindermarket.presentation.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.main.WeaponDetailScreen

@AndroidEntryPoint
class MarketFragment : BaseComposeFragment() {

    private val weaponsViewModel: WeaponsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Загружаем данные из ViewModel
        weaponsViewModel.loadWeapons()
    }

    @Composable
    fun FriendsListScreen(onNavigateToProfile: () -> Unit) {
        Text("Friends List")
        Button(onClick = { onNavigateToProfile() }) {
            Text("Go to Profile")
        }
    }

    @Composable
    override fun ScreenContent() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.WeaponList,
        ) {
            composable<Screen.WeaponList> {
                WeaponListScreen(
                    weaponsViewModel.uiState.collectAsState().value,
                    onSortSelected = weaponsViewModel::onSortSelected,
                    onSearch = weaponsViewModel::onSearch,
                    onOpenDetails = {
                        navController.navigate(Screen.WeaponDetails(it.alias))
                    },
                    modifier = Modifier,
                )
            }
            composable<Screen.WeaponDetails> { backStackEntry ->
                val screen: Screen.WeaponDetails = backStackEntry.toRoute()
                weaponsViewModel.getWeapon(screen.alias)?.let {
                    WeaponDetailScreen(
                        it,
                        onBackClick = { navController.popBackStack() },
                        modifier = Modifier
                    )
                }
            }
        }


    }

    @Serializable
    private sealed class Screen {
        @Serializable
        object WeaponList : Screen()
        @Serializable
        data class WeaponDetails(val alias: String) : Screen()
    }
}
