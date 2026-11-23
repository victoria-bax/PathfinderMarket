package victoria.bakhaeva.pathfindermarket.data.db

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.WeaponCache
import victoria.bakhaeva.pathfindermarket.domain.model.Book
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomWeaponCache @Inject constructor(
    private val bookDao: BookDao,
    private val weaponDao: WeaponDao,
    private val weaponAbilityDao: WeaponAbilityDao,
    private val gson: Gson,
) : WeaponCache {

    override suspend fun saveWeapons(weapons: List<Weapon>) = withContext(Dispatchers.IO) {
        val allWeapons = collectAll(weapons)

        bookDao.insertAll(allWeapons.mapNotNull { it.book }.distinctBy { it.alias }.map {
            BookEntity(alias = it.alias, name = it.name, order = it.order, abbreviation = it.abbreviation)
        })

        weaponDao.insertWeapons(allWeapons.map { it.toEntity() })

        weaponDao.deleteAllParents()
        weaponDao.insertParents(allWeapons.flatMap { weapon ->
            weapon.parents.orEmpty().map { WeaponParentEntity(weaponAlias = weapon.alias, parentName = it) }
        })

        weaponDao.deleteAllChilds()
        weaponDao.insertChilds(allWeapons.flatMap { weapon ->
            weapon.childs.orEmpty().map { WeaponChildEntity(parentAlias = weapon.alias, childAlias = it.alias) }
        })
    }

    override suspend fun getWeapons(): List<Weapon>? = withContext(Dispatchers.IO) {
        if (weaponDao.count() == 0) return@withContext null
        weaponDao.getAllWeapons().map { entity ->
            entity.toDomain(
                parents = weaponDao.getParents(entity.alias),
                childs = weaponDao.getChildWeapons(entity.alias).map { it.toDomain(emptyList(), emptyList()) },
            )
        }
    }

    override suspend fun getWeapon(alias: String): Weapon? = withContext(Dispatchers.IO) {
        weaponDao.getWeaponByAlias(alias)?.let { entity ->
            entity.toDomain(
                parents = weaponDao.getParents(entity.alias),
                childs = weaponDao.getChildWeapons(entity.alias).map { it.toDomain(emptyList(), emptyList()) },
            )
        }
    }

    override suspend fun saveWeaponAbilities(weaponAbilities: List<WeaponAbility>): Boolean = withContext(Dispatchers.IO) {
        if (weaponAbilityDao.count() == weaponAbilities.size) return@withContext false
        weaponAbilityDao.insertAll(
            weaponAbilities.map { WeaponAbilityEntity(alias = it.alias, json = gson.toJson(it)) }
        )
        true
    }

    override suspend fun getWeaponAbilities(): List<WeaponAbility>? = withContext(Dispatchers.IO) {
        val entities = weaponAbilityDao.getAll()
        entities.ifEmpty { null }?.map { gson.fromJson(it.json, WeaponAbility::class.java) }
    }

    override suspend fun getWeaponAbility(alias: String): WeaponAbility? = withContext(Dispatchers.IO) {
        weaponAbilityDao.getByAlias(alias)?.let { gson.fromJson(it.json, WeaponAbility::class.java) }
    }

    private fun collectAll(weapons: List<Weapon>): List<Weapon> {
        val result = mutableListOf<Weapon>()
        fun collect(w: Weapon) {
            result.add(w)
            w.childs?.forEach { collect(it) }
        }
        weapons.forEach { collect(it) }
        return result.distinctBy { it.alias }
    }

    private fun Weapon.toEntity() = WeaponEntity(
        alias = alias,
        name = name,
        engName = engName,
        type = type,
        cost = cost,
        damageS = damageS,
        damageM = damageM,
        criticalRoll = criticalRoll,
        criticalDamage = criticalDamage,
        range = range,
        misfire = misfire,
        capacity = capacity,
        weight = weight,
        special = special,
        description = description,
        proficientCategoryAlias = proficientCategory.alias,
        proficientCategoryName = proficientCategory.name,
        rangeCategoryAlias = rangeCategory.alias,
        rangeCategoryName = rangeCategory.name,
        encumbranceCategoryAlias = encumbranceCategory?.alias,
        encumbranceCategoryName = encumbranceCategory?.name,
        bookAlias = book?.alias,
    )

    private fun WeaponEntity.toDomain(parents: List<String>, childs: List<Weapon>) = Weapon(
        alias = alias,
        name = name,
        engName = engName,
        type = type,
        cost = cost,
        damageS = damageS,
        damageM = damageM,
        criticalRoll = criticalRoll,
        criticalDamage = criticalDamage,
        range = range,
        misfire = misfire,
        capacity = capacity,
        weight = weight,
        special = special,
        description = description,
        proficientCategory = ProficientCategory(name = proficientCategoryName, alias = proficientCategoryAlias),
        rangeCategory = RangeCategory(name = rangeCategoryName, alias = rangeCategoryAlias),
        encumbranceCategory = encumbranceCategoryAlias?.let {
            EncumbranceCategory(name = encumbranceCategoryName, alias = it)
        },
        parents = parents.ifEmpty { null },
        childs = childs.ifEmpty { null },
        book = bookAlias?.let { bookDao.getByAlias(it)?.toDomain() },
    )

    private fun BookEntity.toDomain() = Book(
        alias = alias,
        name = name,
        order = order,
        abbreviation = abbreviation,
    )
}
