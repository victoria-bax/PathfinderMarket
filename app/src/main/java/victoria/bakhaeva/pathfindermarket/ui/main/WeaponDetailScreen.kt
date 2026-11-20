package victoria.bakhaeva.pathfindermarket.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.presentation.weapondetail.WeaponDetailViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeaponDetailScreen(
    weaponAlias: String,
    viewModel: WeaponDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onUpgradeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    viewModel.loadWeapon(weaponAlias)
    Screen(
        state = viewModel.uiState.collectAsState().value,
        modifier = modifier,
        success =
        { weapon ->
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                containerColor = colorScheme.background,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(weapon.name ?: "")
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
                        actions = {
                            Icon(
                                painter = painterResource(R.drawable.ic_upgrade),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable { onUpgradeClick(weapon.alias) }
                            )
                        }
                    )
                },
                content = { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {
                        item {
                            weapon.cost?.let {
                                Text(
                                    text = stringResource(R.string.label_weapon_cost, it),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        item {
                            weapon.damageS?.let {
                                Text(
                                    text = stringResource(R.string.label_damage_s, it),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        item {
                            weapon.damageM?.let {
                                Text(
                                    text = stringResource(R.string.label_damage_m, it),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        item {
                            Column {
//                                var showMore by remember { mutableStateOf(false) }

//                                if (showMore)
                                Column {
                                    // Отображение типа
                                    weapon.type?.let {
                                        Text(
                                            text = stringResource(R.string.label_weapon_type, it),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение критического урона
                                    weapon.criticalRoll?.let {
                                        Text(
                                            text = stringResource(R.string.label_critical_roll, it),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    weapon.criticalDamage?.let {
                                        Text(
                                            text = stringResource(R.string.label_critical_damage, it),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение дальности
                                    weapon.range?.let {
                                        Text(
                                            text = stringResource(R.string.label_range, it),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение веса
                                    weapon.weight?.let {
                                        Text(
                                            text = stringResource(R.string.label_weight, it),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение категории профессии
                                    weapon.proficientCategory.let {
                                        Text(
                                            text = stringResource(R.string.label_proficient_category, it.name ?: ""),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение категории дальности
                                    weapon.rangeCategory.let {
                                        Text(
                                            text = stringResource(R.string.label_range_category, it.name ?: ""),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Отображение категории обременения (если есть)
                                    weapon.encumbranceCategory?.let {
                                        Text(
                                            text = stringResource(R.string.label_encumbrance_category, it.name ?: ""),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Описание оружия
                                    weapon.description?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))

                                    weapon.parents?.let {
                                        Text(
                                            text = stringResource(R.string.label_applies_with),
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        it.forEach { parent ->
                                            //todo clickable weapons
                                            Text(
                                                text = stringResource(R.string.list_item_prefix, parent),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    // Список детей оружия
                                    weapon.childs?.let {
                                        //todo clickable
                                        Text(
                                            text = stringResource(R.string.label_ammunition),
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                        it.forEach { child ->
                                            Text(
                                                text = stringResource(R.string.list_item_prefix, child.name ?: ""),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }

                                /*Text(
                                    text = if (showMore) "Скрыть" else "Подробнее...",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { showMore = !showMore }
                                        .padding(vertical = 8.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                )*/
                            }
                        }
                    }
                },

                )

        }
    )
}