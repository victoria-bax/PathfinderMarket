package victoria.bakhaeva.pathfindermarket.ui.weapons

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon

class WeaponPreviewProvider : PreviewParameterProvider<Weapon> {
    override val values = sequenceOf(
        Weapon(
            alias = "longsword",
            name = "Длинный меч",
            engName = "Longsword",
            type = "Р",
            cost = 15,
            damageS = "1d6",
            damageM = "1d8",
            criticalRoll = "19-20",
            criticalDamage = "x2",
            weight = 4.0,
            description = "Меч длиной около 3,5 фута.",
            proficientCategory = ProficientCategory(name = "Боевое", alias = ProficientAlias.MARTIAL),
            rangeCategory = RangeCategory(name = "Ближнего боя", alias = RangeAlias.MELEE),
            encumbranceCategory = EncumbranceCategory(name = "Одноручное", alias = EncumbranceAlias.ONE_HANDED),
        ),
        Weapon(
            alias = "longbow",
            name = "Длинный лук",
            engName = "Longbow",
            type = "Д",
            cost = 75,
            damageS = "1d6",
            damageM = "1d8",
            criticalRoll = null,
            criticalDamage = "x3",
            range = "100 фт.",
            weight = 3.0,
            description = null,
            proficientCategory = ProficientCategory(name = "Боевое", alias = ProficientAlias.MARTIAL),
            rangeCategory = RangeCategory(name = "Дальнего боя", alias = RangeAlias.RANGED),
            encumbranceCategory = EncumbranceCategory(name = "Двуручное", alias = EncumbranceAlias.TWO_HANDED),
        ),
        Weapon(
            alias = "dagger",
            name = "Кинжал",
            engName = "Dagger",
            type = "Р/К",
            cost = 2,
            damageS = "1d3",
            damageM = "1d4",
            criticalRoll = "19-20",
            criticalDamage = "x2",
            weight = 1.0,
            description = "Универсальное оружие ближнего и дальнего боя.",
            proficientCategory = ProficientCategory(name = "Простое", alias = ProficientAlias.SIMPLE),
            rangeCategory = RangeCategory(name = "Ближнего боя", alias = RangeAlias.MELEE),
            encumbranceCategory = EncumbranceCategory(name = "Лёгкое", alias = EncumbranceAlias.LIGHT),
        ),
    )
}
