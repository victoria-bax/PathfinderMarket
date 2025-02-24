package victoria.bakhaeva.pathfindermarket.ui

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import victoria.bakhaeva.pathfindermarket.presentation.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.BaseComposeFragment
import victoria.bakhaeva.pathfindermarket.ui.WeaponListScreen

@AndroidEntryPoint
class WeaponsFragment : BaseComposeFragment() {

    private val weaponsViewModel: WeaponsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Загружаем данные из ViewModel
        weaponsViewModel.loadWeapons()
    }

    @Composable
    override fun ScreenContent() {
        WeaponListScreen(weaponsViewModel)
    }

}
