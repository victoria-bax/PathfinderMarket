package victoria.bakhaeva.pathfindermarket.data.mapper

import victoria.bakhaeva.pathfindermarket.data.model.WeaponResponse
import victoria.bakhaeva.pathfindermarket.domain.model.Book
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject

class WeaponMapper @Inject constructor() {
    fun map(response: WeaponResponse): Weapon = Weapon(
        alias = response.alias,
        name = response.name,
        engName = response.engName,
        type = response.type,
        cost = response.cost?.let { (it * 100).toInt() },
        damageS = response.damageS.stripHtml(),
        damageM = response.damageM.stripHtml(),
        criticalRoll = response.criticalRoll.stripHtml(),
        criticalDamage = response.criticalDamage.stripHtml(),
        range = response.range.stripHtml(),
        misfire = response.misfire,
        capacity = response.capacity,
        weight = response.weight,
        special = response.special.stripHtml(),
        description = response.description.stripHtml(),
        proficientCategory = ProficientCategory(
            name = response.proficientCategory.name,
            alias = response.proficientCategory.alias,
        ),
        rangeCategory = RangeCategory(
            name = response.rangeCategory.name,
            alias = response.rangeCategory.alias,
        ),
        encumbranceCategory = response.encumbranceCategory?.let {
            EncumbranceCategory(name = it.name, alias = it.alias)
        },
        parents = response.parents,
        childs = response.childs?.map { map(it) },
        book = response.book?.let {
            Book(alias = it.alias, name = it.name, order = it.order, abbreviation = it.abbreviation)
        },
    )

    fun map(responses: List<WeaponResponse>): List<Weapon> = responses.map { map(it) }
}

fun String?.stripHtml(): String? = this?.replace(Regex("<[^>]+>"), "")
