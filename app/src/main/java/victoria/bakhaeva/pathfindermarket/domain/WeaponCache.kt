package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility

interface WeaponCache {
    fun saveWeapons(weapons: List<Weapon>)
    fun getWeapons(): List<Weapon>?
    fun getWeapon(alias: String): Weapon?

    fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>)
    fun getWeaponAbilities(): List<WeaponAbility>?
    fun getWeaponAbility(alias: String): WeaponAbility?

}