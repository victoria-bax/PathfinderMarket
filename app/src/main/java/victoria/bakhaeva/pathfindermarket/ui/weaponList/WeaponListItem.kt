package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold
import victoria.bakhaeva.pathfindermarket.ui.theme.Typography

@Composable
fun WeaponListItem(
    weapon: Weapon,
    onWeaponCLick: (Weapon) -> Unit,
) {
    Column(modifier = Modifier
        .clickable { onWeaponCLick(weapon) }
        .padding(16.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = weapon.name,
                style = Typography.titleLarge
            )
            val cost = when (weapon.cost) {
                null -> "0"
                -1.0 -> "Особая"
                else -> "${weapon.cost} зм"

            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                text = cost,
                style = Typography.titleLarge,
                color = Gold,
            )

        }

        weapon.description?.let {
            Text(
                text = it,
                style = Typography.labelSmall
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
private fun PreviewWeaponListItem() {
    WeaponListItem(
        weapon = Weapon(
            alias = "longsword",
            name = "Длинный меч",
            engName = "Longsword",
            type = "Р",
            cost = 15.0,
            damageS = "1d6",
            damageM = "1d8",
            criticalRoll = "19-20",
            criticalDamage = "x2",
            weight = 4.0,
            description = "Меч длиной около 3,5 фута.",
            proficientCategory = ProficientCategory(
                name = "Особое",
                alias = "martial"
            ),
            rangeCategory = RangeCategory(
                name = "Ближнего боя",
                alias = "melee",
            ),
            encumbranceCategory = EncumbranceCategory(
                name = "Полуторное",
                alias = "oneHanded",
            ),
        ),
        onWeaponCLick = {},
    )
}