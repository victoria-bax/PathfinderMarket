package victoria.bakhaeva.pathfindermarket.ui

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
import androidx.compose.material3.LargeTopAppBar
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
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.ui.theme.DimAmber
import victoria.bakhaeva.pathfindermarket.ui.theme.LanternAmber
import victoria.bakhaeva.pathfindermarket.ui.theme.NightSky
import victoria.bakhaeva.pathfindermarket.ui.theme.NightSurface
import victoria.bakhaeva.pathfindermarket.ui.theme.DawnInk
import victoria.bakhaeva.pathfindermarket.ui.theme.ShadowBrown
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

private data class MenuItem(
    val title: String,
    val subtitle: String,
    val iconRes: Int,
    val onClick: (() -> Unit)?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onWeaponListClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val menuItems = listOf(
        MenuItem(
            title = "Полный список оружия",
            subtitle = "Все виды оружия Pathfinder",
            iconRes = R.drawable.ic_melee,
            onClick = onWeaponListClick,
        ),
        MenuItem(
            title = "Броня",
            subtitle = "Скоро",
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
        MenuItem(
            title = "Снаряжение",
            subtitle = "Скоро",
            iconRes = R.drawable.ic_filter,
            onClick = null,
        ),
        MenuItem(
            title = "Магические предметы",
            subtitle = "Скоро",
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
        MenuItem(
            title = "Зелья и свитки",
            subtitle = "Скоро",
            iconRes = R.drawable.ic_upgrade,
            onClick = null,
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightSky)
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
        // gradient fade into toolbar color at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, NightSky),
                    )
                ),
        )
        Text(
            text = "Pathfinder Market",
            style = Typography.headlineMedium,
            color = DawnInk,
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
                .background(NightSky.copy(alpha = alpha)),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = "Pathfinder Market",
                style = Typography.titleLarge,
                color = DawnInk.copy(alpha = alpha),
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
            .background(NightSurface)
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
            tint = if (isEnabled) LanternAmber else ShadowBrown,
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = Typography.titleMedium,
                color = if (isEnabled) DawnInk else DawnInk.copy(alpha = 0.4f),
            )
            Text(
                text = item.subtitle,
                style = Typography.labelSmall,
                color = if (isEnabled) DimAmber else DawnInk.copy(alpha = 0.25f),
            )
        }
    }
}
