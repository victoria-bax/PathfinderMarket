package victoria.bakhaeva.pathfindermarket.data.db

import com.google.gson.Gson
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.WeaponCache
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomWeaponCache @Inject constructor(
    private val weaponDao: WeaponDao,
    private val weaponAbilityDao: WeaponAbilityDao,
    private val gson: Gson,
) : WeaponCache {

    override fun saveWeapons(weapons: List<Weapon>) {
        weaponDao.insertAll(weapons.map { WeaponEntity(alias = it.alias, json = gson.toJson(it)) })
    }

    override fun getWeapons(): List<Weapon>? {
        val entities = weaponDao.getAll()
        return entities.ifEmpty { null }?.map { gson.fromJson(it.json, Weapon::class.java) }
    }

    override fun getWeapon(alias: String): Weapon? {
        return weaponDao.getByAlias(alias)?.let { gson.fromJson(it.json, Weapon::class.java) }
    }

    override fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>): Boolean {
        val currentCount = weaponAbilityDao.count()
        if (currentCount == weaponAbilities.size) return false
        weaponAbilityDao.insertAll(
            weaponAbilities.map { WeaponAbilityEntity(alias = it.alias, json = gson.toJson(it)) }
        )
        return true
    }

    override fun getWeaponAbilities(): List<WeaponAbility>? {
        val entities = weaponAbilityDao.getAll()
        return entities.ifEmpty { null }?.map { gson.fromJson(it.json, WeaponAbility::class.java) }
    }

    override fun getWeaponAbility(alias: String): WeaponAbility? {
        return weaponAbilityDao.getByAlias(alias)?.let { gson.fromJson(it.json, WeaponAbility::class.java) }
    }
}
