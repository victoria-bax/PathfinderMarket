package victoria.bakhaeva.pathfindermarket.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.formatPrice
import victoria.bakhaeva.pathfindermarket.presentation.weapondetail.WeaponDetailViewModel
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponAbilitiesViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen
import victoria.bakhaeva.pathfindermarket.ui.formatCost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeaponUpgradesListScreen(
    range: String,
    viewModel: WeaponAbilitiesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAbilitySelected: (WeaponAbility) -> Unit,
    modifier: Modifier = Modifier,
) {
    viewModel.loadAbilities(range)
    Screen(
        state = viewModel.uiState.collectAsState().value,
        modifier = modifier,
        success = { abilities ->
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                containerColor = colorScheme.background,
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Улучшения оружия")
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "",
                                modifier = Modifier
                                    .clickable { onBackClick() }
                                    .padding(8.dp),
                            )
                        },
                    )
                },
                content = { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {
                        items(abilities) { ability ->
                            Column(
                                modifier = Modifier
                                    .clickable { onAbilitySelected(ability) },
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = ability.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.End,
                                        text = ability.formatPrice()
                                    )
                                }
                                Text(
                                    text = ability.description
                                )
                            }
                        }
                    }
                },

                )

        }
    )
}