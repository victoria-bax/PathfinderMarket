package victoria.bakhaeva.pathfindermarket.presentation

import victoria.bakhaeva.pathfindermarket.domain.model.Book
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility

fun makeWeapon(
    alias: String = "sword",
    name: String = "Sword",
    cost: Int? = 1500,
    rangeAlias: RangeAlias = RangeAlias.MELEE,
    proficientAlias: ProficientAlias = ProficientAlias.MARTIAL,
) = Weapon(
    alias = alias,
    name = name,
    cost = cost,
    proficientCategory = ProficientCategory(name = "Martial", alias = proficientAlias),
    rangeCategory = RangeCategory(name = "Melee", alias = rangeAlias),
)

fun makeAbility(
    alias: String = "flaming",
    name: String = "Flaming",
    bonusPrice: Int? = 1,
    moneyPrice: Int? = 200000,
) = WeaponAbility(
    alias = alias,
    name = name,
    engName = name,
    aura = "moderate evocation",
    cl = 10,
    bonusPrice = bonusPrice,
    moneyPrice = moneyPrice,
    description = "Deals +1d6 fire damage",
    constructionRequirements = "Craft Magic Arms and Armor",
)
