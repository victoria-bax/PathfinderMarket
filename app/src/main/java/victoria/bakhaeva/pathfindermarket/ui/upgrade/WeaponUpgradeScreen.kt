package victoria.bakhaeva.pathfindermarket.ui.upgrade

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.common.formatPrice
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice.mastercraftPrice
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponUpgradeViewModel
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.countAttackBonus
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.countSum
import victoria.bakhaeva.pathfindermarket.ui.Screen
import victoria.bakhaeva.pathfindermarket.ui.common.components.TextCheckBox
import victoria.bakhaeva.pathfindermarket.ui.common.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeaponUpgradeScreen(
    newAbilityAlias: String?,
    modifier: Modifier = Modifier,
    viewModel: WeaponUpgradeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddAbilityClick: (String) -> Unit,
) {
    LaunchedEffect(newAbilityAlias) { newAbilityAlias?.let { viewModel.addAbility(it) } }
    Screen(
        state = viewModel.uiState.collectAsState().value,
        modifier = modifier,
        success =
        { state ->
            var openImprovementsDialog by remember { mutableStateOf(false) }

            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                containerColor = colorScheme.background,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
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
                                    text = stringResource(R.string.label_total_cost, state.countSum().formatCost()),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            state.countAttackBonus().takeIf { it > 0 }?.let { attackBonus ->
                                item {
                                    Text(
                                        text = stringResource(R.string.label_attack_bonus, attackBonus),
                                    )
                                }
                            }

                            item {
                                Text(
                                    text = stringResource(R.string.label_base_cost, state.weapon.cost.formatCost()),
                                )
                            }

                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    TextCheckBox(
                                        isChecked = state.isMasterwork,
                                        onCheckedChange = viewModel::onMasterworkChange,
                                        text = stringResource(R.string.masterwork)
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
                            item {
                                if (state.isMasterwork) {
                                    if (state.improvements == 0) {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            onClick = {
                                                openImprovementsDialog = true
                                            }
                                        ) {
                                            Text(text = stringResource(R.string.enchant))
                                        }
                                    } else {
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    openImprovementsDialog = true
                                                }
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(stringResource(R.string.label_attack_bonus_value, state.improvements))
                                            Text(
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.fillMaxWidth(),
                                                text = WeaponBonusPrice.prices[state.improvements].formatCost(),
                                                style = MaterialTheme.typography.titleMedium,
                                                color = Gold
                                            )
                                        }
                                    }
                                }
                            }

                            if (state.improvements > 0) {
                                if (state.abilities.isEmpty()) {
                                    item {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            onClick = {
                                                onAddAbilityClick(state.weapon.rangeCategory.alias.value)
                                            }
                                        ) {
                                            Text(stringResource(R.string.add_special_properties))
                                        }
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
                                                text = AnnotatedString.fromHtml(ability.description),
                                                maxLines = 3,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (openImprovementsDialog) {
                            ImprovementsDialog(
                                state = ImprovementsUiState(
                                    selectedImprovement = state.improvements,
                                    improvements = List(5) { it + 1 }
                                ),
                                onImprovementClicked = viewModel::onImprovementClicked,
                                onDismiss = {
                                    openImprovementsDialog = false
                                }
                            )
                        }
                    }
                }
            )
        }
    )
}
