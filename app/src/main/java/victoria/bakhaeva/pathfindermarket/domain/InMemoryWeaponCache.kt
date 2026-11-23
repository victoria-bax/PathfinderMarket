package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryWeaponCache @Inject constructor() : WeaponCache {
    private var weapons: List<Weapon>? = null
    private val weaponAbilities = mutableMapOf<String, List<WeaponAbility>>()

    override suspend fun saveWeapons(weapons: List<Weapon>) {
        this.weapons = weapons
    }

    override suspend fun getWeapons(): List<Weapon>? = weapons

    override suspend fun getWeapon(alias: String): Weapon? = weapons?.firstOrNull { it.alias == alias }

    override suspend fun searchWeapons(query: String): List<Weapon> =
        weapons.orEmpty().filter {
            it.name.contains(query, ignoreCase = true) || it.engName?.contains(query, ignoreCase = true) == true
        }

    override suspend fun filterWeapons(
        rangeAliases: Set<RangeAlias>,
        proficientAliases: Set<ProficientAlias>,
        encumbranceAliases: Set<EncumbranceAlias>,
        bookAliases: Set<BookAlias>,
    ): List<Weapon> = weapons.orEmpty().filter { weapon ->
        (rangeAliases.isEmpty() || weapon.rangeCategory.alias in rangeAliases)
                && (proficientAliases.isEmpty() || weapon.proficientCategory.alias in proficientAliases)
                && (encumbranceAliases.isEmpty() || weapon.encumbranceCategory?.alias in encumbranceAliases)
                && (bookAliases.isEmpty() || weapon.book?.alias in bookAliases)
    }

    override suspend fun saveWeaponAbilities(rangeType: String, weaponAbilities: List<WeaponAbility>) {
        this.weaponAbilities[rangeType] = weaponAbilities
    }

    override suspend fun getWeaponAbilities(rangeType: String): List<WeaponAbility>? =
        weaponAbilities[rangeType]

    override suspend fun getWeaponAbility(alias: String): WeaponAbility? =
        weaponAbilities.values.flatten().firstOrNull { it.alias == alias }
}
