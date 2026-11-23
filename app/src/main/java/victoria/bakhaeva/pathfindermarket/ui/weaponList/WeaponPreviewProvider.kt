package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon

class WeaponPreviewProvider : PreviewParameterProvider<Weapon> {
    override val values = sequenceOf(
        Weapon(
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
            proficientCategory = ProficientCategory(name = "Боевое", alias = "martial"),
            rangeCategory = RangeCategory(name = "Ближнего боя", alias = "melee"),
            encumbranceCategory = EncumbranceCategory(name = "Одноручное", alias = "oneHanded"),
        ),
        Weapon(
            alias = "longbow",
            name = "Длинный лук",
            engName = "Longbow",
            type = "Д",
            cost = 75.0,
            damageS = "1d6",
            damageM = "1d8",
            criticalRoll = null,
            criticalDamage = "x3",
            range = "100 фт.",
            weight = 3.0,
            description = null,
            proficientCategory = ProficientCategory(name = "Боевое", alias = "martial"),
            rangeCategory = RangeCategory(name = "Дальнего боя", alias = "ranged"),
            encumbranceCategory = EncumbranceCategory(name = "Двуручное", alias = "twoHanded"),
        ),
        Weapon(
            alias = "dagger",
            name = "Кинжал",
            engName = "Dagger",
            type = "Р/К",
            cost = 2.0,
            damageS = "1d3",
            damageM = "1d4",
            criticalRoll = "19-20",
            criticalDamage = "x2",
            weight = 1.0,
            description = "Универсальное оружие ближнего и дальнего боя.",
            proficientCategory = ProficientCategory(name = "Простое", alias = "simple"),
            rangeCategory = RangeCategory(name = "Ближнего боя", alias = "melee"),
            encumbranceCategory = EncumbranceCategory(name = "Лёгкое", alias = "light"),
        ),
    )
}
