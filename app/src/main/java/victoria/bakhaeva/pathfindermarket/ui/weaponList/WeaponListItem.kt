package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.ui.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.DarkGold
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold
import victoria.bakhaeva.pathfindermarket.ui.theme.PathfinderMarketTheme
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography
import victoria.bakhaeva.pathfindermarket.ui.theme.colorScheme

@Composable
fun WeaponListItem(
    weapon: Weapon,
    onWeaponCLick: (Weapon) -> Unit,
) {
    Column(modifier = Modifier
        .padding(6.dp)
        .background(
            color = colorScheme.surface,
            shape = RoundedCornerShape(9.dp),
        )
        .clickable { onWeaponCLick(weapon) }
        .padding(12.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = weapon.name,
                style = Typography.titleLarge
            )
            val cost = weapon.cost.formatCost()
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                text = cost,
                style = Typography.titleLarge,
                color = DarkGold,
            )
        }

        weapon.description?.let {
            Text(
                text = it,
                style = Typography.labelSmall,
                maxLines = 5,
            )
        }

        Row {
            Range.entries.firstOrNull {
                it.value == weapon.rangeCategory.alias
            }?.let {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .heightIn(max = 36.dp),
                    painter = painterResource(it.icon),
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp),
                text = "${weapon.proficientCategory.name}, ${weapon.encumbranceCategory?.name ?: ""}"
            )
        }
    }
}

@Composable
@Preview
private fun PreviewWeaponListItem(
    @PreviewParameter(WeaponPreviewProvider::class) weapon: Weapon,
) {
    PathfinderMarketTheme {
        WeaponListItem(weapon = weapon, onWeaponCLick = {})
    }
}