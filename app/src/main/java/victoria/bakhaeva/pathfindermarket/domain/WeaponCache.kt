package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.model.Weapon

interface WeaponCache {
    fun save(weapons: List<Weapon>)
    fun get(): List<Weapon>?
    fun get(alias: String): Weapon?
}