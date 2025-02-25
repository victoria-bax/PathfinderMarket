package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import javax.inject.Inject

class InMemoryWeaponCache @Inject constructor() : WeaponCache {
    private var weapons: List<Weapon>? = null

    override fun save(weapons: List<Weapon>) {
        this.weapons = weapons
    }

    override fun get(): List<Weapon>? = weapons

    override fun get(alias: String): Weapon? = weapons?.firstOrNull { it.alias == alias }
}