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
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.formatPrice
import victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade.WeaponAbilitiesViewModel
import victoria.bakhaeva.pathfindermarket.ui.Screen

@Composable
internal fun WeaponAbilityDetailsScreen(
    weaponAbilityAlias: String,
    viewModel: WeaponAbilitiesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    LaunchedEffect(weaponAbilityAlias) {
        viewModel.loadAbility(weaponAbilityAlias)
    }
    val state by viewModel.abilityState.collectAsState()
    Screen(
        state = state,
        modifier = Modifier,
        success = {
            WeaponAbilityDetailScaffold(
                weaponAbility = it,
                onBackClick = onBackClick,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeaponAbilityDetailScaffold(
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