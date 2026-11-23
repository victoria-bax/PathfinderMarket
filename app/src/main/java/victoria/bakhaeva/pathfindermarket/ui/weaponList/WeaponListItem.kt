package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListItemUiState
import victoria.bakhaeva.pathfindermarket.ui.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.PathfinderMarketPreview
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

@Composable
fun WeaponListItem(
    weapon: WeaponListItemUiState,
    onWeaponCLick: (WeaponListItemUiState) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .background(color = colorScheme.surface, shape = RoundedCornerShape(9.dp))
            .clickable { onWeaponCLick(weapon) }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = weapon.name,
                style = Typography.titleLarge,
                color = colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = weapon.cost.formatCost(),
                style = Typography.titleLarge,
                color = colorScheme.primary,
            )
        }

        weapon.description?.let {
            Text(
                text = it,
                style = Typography.labelMedium,
                color = colorScheme.onSurface,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .heightIn(max = 36.dp),
                painter = painterResource(weapon.rangeIconRes),
                contentDescription = null,
                tint = colorScheme.onSurface,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp),
                text = "${weapon.proficientCategoryName}, ${weapon.encumbranceCategoryName ?: ""}",
                color = colorScheme.onSurface,
            )
        }
    }
}

@Composable
@Preview
private fun PreviewWeaponListItem(
    @PreviewParameter(WeaponPreviewProvider::class) weapon: Weapon,
) {
    PathfinderMarketPreview {
        WeaponListItem(
            weapon = WeaponListItemUiState(
                alias = weapon.alias,
                name = weapon.name,
                cost = weapon.cost,
                description = weapon.description,
                rangeIconRes = Range.from(weapon.rangeCategory.alias).icon,
                proficientCategoryName = weapon.proficientCategory.name,
                encumbranceCategoryName = weapon.encumbranceCategory?.name,
            ),
            onWeaponCLick = {},
        )
    }
}
