package victoria.bakhaeva.pathfindermarket.ui.upgrade

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.End
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.ui.common.formatPrice
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponAbilitiesViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen

@Composable
internal fun WeaponAbilitiesListScreen(
    viewModel: WeaponAbilitiesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAbilitySelected: (WeaponAbility) -> Unit,
    onAbilityDetails: (WeaponAbility) -> Unit,
    modifier: Modifier = Modifier,
) {
    Screen(
        state = viewModel.uiState.collectAsState().value,
        modifier = modifier,
        success = { abilities ->
            WeaponAbilitiesListContent(
                modifier,
                onBackClick,
                abilities,
                onAbilityDetails,
                onAbilitySelected,
            )
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WeaponAbilitiesListContent(
    modifier: Modifier,
    onBackClick: () -> Unit,
    abilities: List<WeaponAbility>,
    onAbilityDetails: (WeaponAbility) -> Unit,
    onAbilitySelected: (WeaponAbility) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
                title = {
                    Text(stringResource(R.string.weapon_upgrades_title))
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
                            .clickable { onAbilityDetails(ability) },
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = ability.name,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                text = ability.formatPrice()
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = AnnotatedString.fromHtml(ability.description),
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Button(
                                onClick = {
                                    onAbilitySelected(ability)
                                }
                            ) {
                                Text(stringResource(R.string.enchant))
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
@Preview
private fun PreviewWeaponAbilitiesListDark() {
    PreviewDark {
        WeaponAbilitiesListContent(
            modifier = Modifier,
            onBackClick = {},
            onAbilitySelected = {},
            onAbilityDetails = {},
            abilities = listOf(
                WeaponAbility(
                    alias = "test",
                    name = "Test",
                    engName = "Test",
                    aura = "Aura",
                    cl = 10,
                    bonusPrice = 1,
                    moneyPrice = 2000,
                    description = "<p>Test</p>",
                    constructionRequirements = "constructionRequirements",
                )
            )
        )
    }
}

@Composable
@Preview
private fun PreviewWeaponAbilitiesListLight() {
    PreviewLight {
        WeaponAbilitiesListContent(
            modifier = Modifier,
            onBackClick = {},
            onAbilitySelected = {},
            onAbilityDetails = {},
            abilities = listOf(
                WeaponAbility(
                    alias = "test",
                    name = "Test",
                    engName = "Test",
                    aura = "Aura",
                    cl = 10,
                    bonusPrice = 1,
                    moneyPrice = 2000,
                    description = "<p>Test</p>",
                    constructionRequirements = "constructionRequirements",
                )
            )
        )
    }
}
