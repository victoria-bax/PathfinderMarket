package victoria.bakhaeva.pathfindermarket.ui

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.AndroidEntryPoint
import victoria.bakhaeva.pathfindermarket.presentation.WeaponsViewModel
import victoria.bakhaeva.pathfindermarket.ui.BaseComposeFragment
import victoria.bakhaeva.pathfindermarket.ui.WeaponListScreen
import victoria.bakhaeva.pathfindermarket.ui.weaponList.SortDialog

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
        WeaponListScreen(
            weaponsViewModel.uiState.collectAsState().value,
            onSortSelected = weaponsViewModel::onSortSelected,
        )
    }

}
