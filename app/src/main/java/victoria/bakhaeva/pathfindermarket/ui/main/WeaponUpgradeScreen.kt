package victoria.bakhaeva.pathfindermarket.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.data.model.formatPrice
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice.mastercraftPrice
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponUpgradeViewModel
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.countAttackBonus
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.countSum
import victoria.bakhaeva.pathfindermarket.ui.Screen
import victoria.bakhaeva.pathfindermarket.ui.View.TextCheckBox
import victoria.bakhaeva.pathfindermarket.ui.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeaponUpgradeScreen(
    weaponAlias: String,
    newAbilityAlias: String?,
    modifier: Modifier = Modifier,
    viewModel: WeaponUpgradeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddAbilityClick: (String) -> Unit,
) {
    viewModel.loadWeapon(weaponAlias)
    newAbilityAlias?.let {
        viewModel.addAbility(it)
    }
    Screen(
        state = viewModel.uiState.collectAsState().value,
        modifier = modifier,
        success =
        { state ->
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                containerColor = colorScheme.background,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(state.weapon.name)
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
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            item {
                                Text(
                                    text = "Сумма: ${state.countSum().formatCost()}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            state.countAttackBonus().takeIf { it > 0 }?.let { attackBonus ->
                                item {
                                    Text(
                                        text = "Бонус к атаке: +${attackBonus}",
                                    )
                                }
                            }

                            item {
                                Text(
                                    text = "Базовая цена: ${state.weapon.cost.formatCost()}",
                                )
                            }

                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    TextCheckBox(
                                        isChecked = state.isMasterwork,
                                        onCheckedChange = viewModel::onMasterworkChange,
                                        text = "Искусно сделано"
                                    )
                                    if (state.isMasterwork) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.End,
                                            text = mastercraftPrice.formatCost(),
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = Gold,
                                        )
                                    }
                                }
                            }

                            if (state.abilities.isEmpty()) {
                                item {
                                    Text("Магических усовершенствований не добавлено")
                                }
                            } else {
                                items(state.abilities) { ability ->
                                    Column {
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
                        }
                        FloatingActionButton(
                            onClick = {
                                onAddAbilityClick(state.weapon.rangeCategory.alias)
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Icon(Icons.Filled.Add, "")
                        }
                    }
                },
            )
        }
    )
}