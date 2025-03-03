package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryWeaponCache @Inject constructor() : WeaponCache {
    private var weapons: List<Weapon>? = null
    private var weaponAbilities: List<WeaponAbility>? = null

    override fun saveWeapons(weapons: List<Weapon>) {
        this.weapons = weapons
    }

    override fun getWeapons(): List<Weapon>? = weapons

    override fun getWeapon(alias: String): Weapon? = weapons?.firstOrNull { it.alias == alias }

    override fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>) {
        this.weaponAbilities = weaponAbilities
    }

    override fun getWeaponAbilities(): List<WeaponAbility>? = weaponAbilities

    override fun getWeaponAbility(alias: String): WeaponAbility? = weaponAbilities?.firstOrNull { it.alias == alias }
}