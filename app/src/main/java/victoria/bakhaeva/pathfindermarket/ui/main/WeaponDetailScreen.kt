package victoria.bakhaeva.pathfindermarket.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import victoria.bakhaeva.pathfindermarket.data.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.data.model.Weapon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponDetailScreen(
    weapon: Weapon,
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
                    Text(weapon.name)
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable { onBackClick() }
                            .padding(8.dp),
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                weapon.cost?.let {
                    Text(text = "Цена: $it зм", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                weapon.damageS?.let {
                    Text(text = "Урон (S): $it", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                weapon.damageM?.let {
                    Text(text = "Урон (M): $it", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                var showMore by remember { mutableStateOf(false) }

                if (showMore) Column {
                    // Отображение типа
                    weapon.type?.let {
                        Text(text = "Тип: $it", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение критического урона
                    weapon.criticalRoll?.let {
                        Text(
                            text = "Критический бросок: $it",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    weapon.criticalDamage?.let {
                        Text(
                            text = "Критический урон: $it",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение дальности
                    weapon.range?.let {
                        Text(text = "Дальность: $it", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение веса
                    weapon.weight?.let {
                        Text(text = "Вес: $it фнт", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение категории профессии
                    weapon.proficientCategory.let {
                        Text(
                            text = "Категория профессии: ${it.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение категории дальности
                    weapon.rangeCategory.let {
                        Text(
                            text = "Категория дальности: ${it.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Отображение категории обременения (если есть)
                    weapon.encumbranceCategory?.let {
                        Text(
                            text = "Категория обременения: ${it.name})",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Описание оружия
                    Text(text = weapon.description, style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.height(16.dp))

                    weapon.parents?.let {
                        Text(
                            text = "Применяется с:",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        it.forEach { parent ->
                            //todo clickable weapons
                            Text(text = "- $parent", style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Список детей оружия
                    weapon.childs?.let {
                        Text(text = "Дети оружия:", style = MaterialTheme.typography.headlineSmall)
                        it.forEach { child ->
                            Text(
                                text = "- ${child.name}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Text(
                    text = if (showMore) "Скрыть" else "Подробнее...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showMore = !showMore }
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )

            }
        },

        )

}

@Preview(showBackground = true)
@Composable
fun PreviewWeaponDetailScreen() {
    val exampleWeapon = Weapon(
        alias = "falcata",
        name = "Фальката",
        engName = "Falcata",
        type = "Р",
        cost = 18.0,
        damageS = "1d6",
        damageM = "1d8",
        criticalRoll = "19-20",
        criticalDamage = "x3",
        range = null,
        misfire = null,
        capacity = null,
        weight = 4.0,
        special = null,
        description = "У фалькаты заточеное с одной стороны лезвие, которое вогнуто у рукояти, расширяется к середине клинка и сужается возле острия. Эта форма распределяет вес таким образом, что фальката способна нанести удар с инерцией топора, сохраняя при этом более длинную режущую кромку меча и некоторую легкость для выполнения удара. Рукоятка, как правило, крючковидная, конец часто стилизован в форме лошади или птицы.",
        proficientCategory = ProficientCategory(name = "Экзотическое", alias = "exotic"),
        rangeCategory = RangeCategory(name = "Ближнего боя", alias = "melee"),
        encumbranceCategory = EncumbranceCategory(name = "Полуторное", alias = "oneHanded"),
        parents = null,
        childs = null
    )
    WeaponDetailScreen(weapon = exampleWeapon, onBackClick = {})
}
