package victoria.bakhaeva.pathfindermarket.ui.upgrade

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import victoria.bakhaeva.pathfindermarket.R
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.ui.common.formatPrice
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponAbilityDetailsViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight

@Composable
internal fun WeaponAbilityDetailsScreen(
    viewModel: WeaponAbilityDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    Screen(
        state = state,
        modifier = Modifier,
        success = {
            WeaponAbilityDetailsContent(
                weaponAbility = it,
                onBackClick = onBackClick,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeaponAbilityDetailsContent(
    weaponAbility: WeaponAbility,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
                title = {
                    Text(weaponAbility.name)
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
                item {
                    Text(stringResource(R.string.label_aura, weaponAbility.aura))
                }
                item {
                    Text(stringResource(R.string.label_caster_level, weaponAbility.cl))
                }
                item {
                    Text(stringResource(R.string.label_price, weaponAbility.formatPrice()))
                }
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = AnnotatedString.fromHtml(weaponAbility.description)
                    )
                }
            }
        }
    )
}

private val previewAbility = WeaponAbility(
    alias = "flaming",
    name = "Пылающее",
    engName = "Flaming",
    aura = "Слабая призыв",
    cl = 10,
    bonusPrice = 1,
    moneyPrice = 0,
    description = "<p>По команде владельца клинок оружия охватывает огонь, причиняя дополнительно 1d6 урона огнём при каждом попадании.</p>",
    constructionRequirements = "Создать магическое оружие, воспламенение",
)

@Preview(heightDp = 500)
@Composable
private fun PreviewWeaponAbilityDetailsDark() {
    PreviewDark {
        WeaponAbilityDetailsContent(weaponAbility = previewAbility, onBackClick = {})
    }
}

@Preview(heightDp = 500)
@Composable
private fun PreviewWeaponAbilityDetailsLight() {
    PreviewLight {
        WeaponAbilityDetailsContent(weaponAbility = previewAbility, onBackClick = {})
    }
}