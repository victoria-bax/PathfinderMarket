package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.WeaponsViewModel

@Composable
internal fun WeaponListScreen(viewModel: WeaponsViewModel) {
// todo move all functions out of here to make previews
    when (val value = viewModel.uiState.collectAsState().value) {
        is UIState.Loading -> {
            // Показываем индикатор загрузки
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UIState.Success -> {
            // Показываем список оружия
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(value.data.size) { weapon ->
                    WeaponListItem(weapon = value.data[weapon])
                }
            }
        }

        is UIState.Error -> {
            // Показываем сообщение об ошибке
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка: ${value.exception.message}")
            }
        }
    }
}

@Composable
fun WeaponListItem(weapon: Weapon) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Name: ${weapon.name}")
        Text(text = "Cost: ${weapon.cost}")
        Text(text = "Description: ${weapon.description}")
    }
}
