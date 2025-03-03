package victoria.bakhaeva.pathfindermarket.ui.upgrade

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice
import victoria.bakhaeva.pathfindermarket.ui.View.TextCheckBox
import victoria.bakhaeva.pathfindermarket.ui.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.AllFilters
import victoria.bakhaeva.pathfindermarket.ui.weaponList.state.FilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImprovementsDialog(
    state: ImprovementsUiState,
    onImprovementClicked: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(WeaponBonusPrice.maxBonus) { index ->
                    val bonus = index + 1
                    Row(modifier = Modifier
                        .clickable {
                            onImprovementClicked(bonus)
                            onDismiss()
                        }.padding(8.dp)
                        .background(
                            if (bonus == state.selectedImprovement) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.secondaryContainer
                        ), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Бонус +$bonus",
                            color = if (bonus == state.selectedImprovement) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.titleMedium,
                            color = Gold,
                            text = WeaponBonusPrice.prices[bonus].formatCost()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFilterDialog() {
    ImprovementsDialog(
        ImprovementsUiState(selectedImprovement = 1, improvements = List(5) { it + 1 }),
        {},
        {},
    )
}