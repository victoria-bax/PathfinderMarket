package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon

interface WeaponCache {
    fun saveWeapons(weapons: List<Weapon>)
    fun getWeapons(): List<Weapon>?
    fun getWeapon(alias: String): Weapon?

    fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>): Boolean
    fun getWeaponAbilities(): List<WeaponAbility>?
    fun getWeaponAbility(alias: String): WeaponAbility?
}
