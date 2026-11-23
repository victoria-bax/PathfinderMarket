package victoria.bakhaeva.pathfindermarket.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.ui.theme.NightSky
import victoria.bakhaeva.pathfindermarket.ui.theme.NightSurface
import victoria.bakhaeva.pathfindermarket.ui.theme.ShadowBrown
import victoria.bakhaeva.pathfindermarket.ui.theme.DimAmber

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by transition.animateFloat(
        initialValue = -600f,
        targetValue = 1400f,
        animationSpec = infiniteRepeatable(tween(1200, easing = FastOutLinearInEasing)),
        label = "shimmerX",
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            ShadowBrown,
            NightSurface,
            DimAmber.copy(alpha = 0.15f),
            NightSurface,
            ShadowBrown,
        ),
        start = Offset(shimmerX, 0f),
        end = Offset(shimmerX + 600f, 300f),
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NightSky)
            .padding(vertical = 4.dp),
        userScrollEnabled = false,
    ) {
        items(7) {
            ShimmerWeaponItem(shimmerBrush)
        }
    }
}

@Composable
private fun ShimmerWeaponItem(brush: Brush) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(NightSurface)
            .padding(12.dp),
    ) {
        // title row — name + cost
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush),
            )
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // description lines
        repeat(3) { i ->
            Box(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .height(12.dp)
                    .fillMaxWidth(if (i == 2) 0.6f else 1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // icon + category row
        Row {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .width(140.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush),
            )
        }
    }
}

@Composable
@Preview
private fun PreviewLoadingScreen() {
    LoadingScreen()
}
