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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewDark
import victoria.bakhaeva.pathfindermarket.ui.theme.PreviewLight
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice
import victoria.bakhaeva.pathfindermarket.ui.common.components.TextCheckBox
import victoria.bakhaeva.pathfindermarket.ui.common.formatCost
import victoria.bakhaeva.pathfindermarket.ui.theme.Gold
import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.presentation.model.FilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImprovementsDialog(
    state: ImprovementsUiState,
    onImprovementClicked: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        ImprovementsDialogContent(state, onImprovementClicked, onDismiss)
    }
}

@Composable
private fun ImprovementsDialogContent(
    state: ImprovementsUiState,
    onImprovementClicked: (Int) -> Unit,
    onDismiss: () -> Unit,
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
                    }
                    .background(
                        if (bonus == state.selectedImprovement) MaterialTheme.colorScheme.onSecondaryContainer else Color.Transparent
                    )
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.bonus_plus, bonus),
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

@Preview
@Composable
private fun PreviewImprovementsDialogDark() {
    PreviewDark {
        ImprovementsDialogContent(
            ImprovementsUiState(selectedImprovement = 1, improvements = List(5) { it + 1 }),
            {},
            {},
        )
    }
}

@Preview
@Composable
private fun PreviewImprovementsDialogLight() {
    PreviewLight {
        ImprovementsDialogContent(
            ImprovementsUiState(selectedImprovement = 1, improvements = List(5) { it + 1 }),
            {},
            {},
        )
    }
}