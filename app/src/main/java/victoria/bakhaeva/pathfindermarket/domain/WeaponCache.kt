package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon

interface WeaponCache {
    suspend fun saveWeapons(weapons: List<Weapon>)
    suspend fun getWeapons(): List<Weapon>?
    suspend fun getWeapon(alias: String): Weapon?
    suspend fun searchWeapons(query: String): List<Weapon>
    suspend fun filterWeapons(
        rangeAliases: Set<RangeAlias>,
        proficientAliases: Set<ProficientAlias>,
        encumbranceAliases: Set<EncumbranceAlias>,
        bookAliases: Set<BookAlias>,
    ): List<Weapon>

    suspend fun saveWeaponAbilities(rangeType: String, weaponAbilities: List<WeaponAbility>)
    suspend fun getWeaponAbilities(rangeType: String): List<WeaponAbility>?
    suspend fun getWeaponAbility(alias: String): WeaponAbility?
}
