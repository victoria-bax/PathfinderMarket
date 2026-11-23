package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon

interface WeaponCache {
    suspend fun saveWeapons(weapons: List<Weapon>)
    suspend fun getWeapons(): List<Weapon>?
    suspend fun getWeapon(alias: String): Weapon?
    suspend fun searchWeapons(query: String): List<Weapon>

    suspend fun saveWeaponAbilities(rangeType: String, weaponAbilities: List<WeaponAbility>)
    suspend fun getWeaponAbilities(rangeType: String): List<WeaponAbility>?
    suspend fun getWeaponAbility(alias: String): WeaponAbility?
}
