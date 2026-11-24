package victoria.bakhaeva.pathfindermarket.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.common.StatusBarAppearance
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

private data class MenuItem(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    val iconRes: Int,
    val onClick: (() -> Unit)?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onWeaponListClick: () -> Unit,
) {
    StatusBarAppearance(lightIcons = false)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val menuItems = listOf(
        MenuItem(
            titleRes = R.string.home_menu_weapons,
            subtitleRes = R.string.home_menu_weapons_subtitle,
            iconRes = R.drawable.ic_melee,
            onClick = onWeaponListClick,
        ),
        MenuItem(
            titleRes = R.string.home_menu_armor,
            subtitleRes = R.string.home_menu_coming_soon,
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
        MenuItem(
            titleRes = R.string.home_menu_gear,
            subtitleRes = R.string.home_menu_coming_soon,
            iconRes = R.drawable.ic_filter,
            onClick = null,
        ),
        MenuItem(
            titleRes = R.string.home_menu_magic,
            subtitleRes = R.string.home_menu_coming_soon,
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
        MenuItem(
            titleRes = R.string.home_menu_potions,
            subtitleRes = R.string.home_menu_coming_soon,
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                BannerHeader(scrollBehavior)
            }
            items(menuItems) { item ->
                HomeMenuItem(item)
            }
        }

        CollapsedToolbar(scrollBehavior)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BannerHeader(scrollBehavior: TopAppBarScrollBehavior) {
    val bg = MaterialTheme.colorScheme.background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .graphicsLayer {
                alpha = 1f - scrollBehavior.state.collapsedFraction
                translationY = -scrollBehavior.state.collapsedFraction * 80f
            },
    ) {
        Image(
            painter = painterResource(R.drawable.market_banner),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, bg),
                    )
                ),
        )
        Text(
            text = stringResource(R.string.home_title),
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .graphicsLayer { alpha = 1f - scrollBehavior.state.collapsedFraction },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsedToolbar(scrollBehavior: TopAppBarScrollBehavior) {
    val alpha = scrollBehavior.state.collapsedFraction
    if (alpha > 0f) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.background.copy(alpha = alpha)),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = stringResource(R.string.home_title),
                style = Typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun HomeMenuItem(item: MenuItem) {
    val isEnabled = item.onClick != null
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(
                if (isEnabled) Modifier.clickable { item.onClick?.invoke() }
                else Modifier
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(item.iconRes),
            contentDescription = null,
            tint = if (isEnabled) MaterialTheme.colorScheme.primary
                   else MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(item.titleRes),
                style = Typography.titleMedium,
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            )
            Text(
                text = stringResource(item.subtitleRes),
                style = Typography.labelSmall,
                color = if (isEnabled) MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
private fun PreviewHomeScreenDark() {
    PreviewDark {
        HomeScreen(onWeaponListClick = {})
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
private fun PreviewHomeScreenLight() {
    PreviewLight {
        HomeScreen(onWeaponListClick = {})
    }
}
