package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryWeaponCache @Inject constructor() : WeaponCache {
    private var weapons: List<Weapon>? = null
    private var weaponAbilities: List<WeaponAbility>? = null

    override suspend fun saveWeapons(weapons: List<Weapon>) {
        this.weapons = weapons
    }

    override suspend fun getWeapons(): List<Weapon>? = weapons

    override suspend fun getWeapon(alias: String): Weapon? = weapons?.firstOrNull { it.alias == alias }

    override suspend fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>): Boolean {
        if (this.weaponAbilities == weaponAbilities) return false
        this.weaponAbilities = weaponAbilities
        return true
    }

    override suspend fun getWeaponAbilities(): List<WeaponAbility>? = weaponAbilities

    override suspend fun getWeaponAbility(alias: String): WeaponAbility? =
        weaponAbilities?.firstOrNull { it.alias == alias }
}
