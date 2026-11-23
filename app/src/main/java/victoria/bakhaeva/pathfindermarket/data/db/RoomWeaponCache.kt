package victoria.bakhaeva.pathfindermarket.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.domain.WeaponCache
import victoria.bakhaeva.pathfindermarket.domain.model.Book
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientCategory
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeCategory
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomWeaponCache @Inject constructor(
    private val bookDao: BookDao,
    private val weaponDao: WeaponDao,
    private val weaponAbilityDao: WeaponAbilityDao,
) : WeaponCache {

    override suspend fun saveWeapons(weapons: List<Weapon>) = withContext(Dispatchers.IO) {
        val allWeapons = collectAll(weapons)

        bookDao.insertAll(allWeapons.mapNotNull { it.book }.distinctBy { it.alias }.map {
            BookEntity(alias = it.alias.value, name = it.name, order = it.order, abbreviation = it.abbreviation)
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
        weaponDao.getAllWeapons().mapNotNull { it.toDomainOrNull(
            parents = weaponDao.getParents(it.alias),
            childs = weaponDao.getChildWeapons(it.alias).mapNotNull { c -> c.toDomainOrNull(emptyList(), emptyList()) },
        ) }
    }

    override suspend fun getWeapon(alias: String): Weapon? = withContext(Dispatchers.IO) {
        weaponDao.getWeaponByAlias(alias)?.toDomainOrNull(
            parents = weaponDao.getParents(alias),
            childs = weaponDao.getChildWeapons(alias).mapNotNull { it.toDomainOrNull(emptyList(), emptyList()) },
        )
    }

    override suspend fun searchWeapons(query: String): List<Weapon> = withContext(Dispatchers.IO) {
        weaponDao.searchWeapons(query).mapNotNull { entity ->
            entity.toDomainOrNull(
                parents = weaponDao.getParents(entity.alias),
                childs = weaponDao.getChildWeapons(entity.alias).mapNotNull { it.toDomainOrNull(emptyList(), emptyList()) },
            )
        }
    }

    override suspend fun filterWeapons(
        rangeAliases: Set<RangeAlias>,
        proficientAliases: Set<ProficientAlias>,
        encumbranceAliases: Set<EncumbranceAlias>,
        bookAliases: Set<BookAlias>,
    ): List<Weapon> = withContext(Dispatchers.IO) {
        weaponDao.getAllWeapons().mapNotNull { entity ->
            entity.toDomainOrNull(
                parents = weaponDao.getParents(entity.alias),
                childs = weaponDao.getChildWeapons(entity.alias).mapNotNull { it.toDomainOrNull(emptyList(), emptyList()) },
            )
        }.filter { weapon ->
            (rangeAliases.isEmpty() || weapon.rangeCategory.alias in rangeAliases)
                    && (proficientAliases.isEmpty() || weapon.proficientCategory.alias in proficientAliases)
                    && (encumbranceAliases.isEmpty() || weapon.encumbranceCategory?.alias in encumbranceAliases)
                    && (bookAliases.isEmpty() || weapon.book?.alias in bookAliases)
        }
    }

    override suspend fun saveWeaponAbilities(rangeType: String, weaponAbilities: List<WeaponAbility>): Unit = withContext(Dispatchers.IO) {
        weaponAbilityDao.insertAll(weaponAbilities.map { it.toEntity(rangeType) })
    }

    override suspend fun getWeaponAbilities(rangeType: String): List<WeaponAbility>? = withContext(Dispatchers.IO) {
        weaponAbilityDao.getByRangeType(rangeType).ifEmpty { null }?.map { it.toDomain() }
    }

    override suspend fun getWeaponAbility(alias: String): WeaponAbility? = withContext(Dispatchers.IO) {
        weaponAbilityDao.getByAlias(alias)?.toDomain()
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
        proficientCategoryAlias = proficientCategory.alias.value,
        proficientCategoryName = proficientCategory.name,
        rangeCategoryAlias = rangeCategory.alias.value,
        rangeCategoryName = rangeCategory.name,
        encumbranceCategoryAlias = encumbranceCategory?.alias?.value,
        encumbranceCategoryName = encumbranceCategory?.name,
        bookAlias = book?.alias?.value,
    )

    private fun WeaponEntity.toDomainOrNull(parents: List<String>, childs: List<Weapon>): Weapon? {
        val proficient = ProficientAlias.entries.firstOrNull { it.value == proficientCategoryAlias } ?: return null
        val range = RangeAlias.entries.firstOrNull { it.value == rangeCategoryAlias } ?: return null
        val encumbrance = encumbranceCategoryAlias?.let { EncumbranceAlias.entries.firstOrNull { e -> e.value == it } }
        return Weapon(
            alias = alias,
            name = name,
            engName = engName,
            type = type,
            cost = cost,
            damageS = damageS,
            damageM = damageM,
            criticalRoll = criticalRoll,
            criticalDamage = criticalDamage,
            range = this.range,
            misfire = misfire,
            capacity = capacity,
            weight = weight,
            special = special,
            description = description,
            proficientCategory = ProficientCategory(name = proficientCategoryName, alias = proficient),
            rangeCategory = RangeCategory(name = rangeCategoryName, alias = range),
            encumbranceCategory = encumbrance?.let { EncumbranceCategory(name = encumbranceCategoryName, alias = it) },
            parents = parents.ifEmpty { null },
            childs = childs.ifEmpty { null },
            book = bookAlias?.let { bookDao.getByAlias(it)?.toDomain() },
        )
    }

    private fun BookEntity.toDomain(): Book? {
        val alias = BookAlias.entries.firstOrNull { it.value == alias } ?: return null
        return Book(alias = alias, name = name, order = order, abbreviation = abbreviation)
    }

    private fun WeaponAbility.toEntity(rangeType: String) = WeaponAbilityEntity(
        alias = alias,
        rangeType = rangeType,
        name = name,
        engName = engName,
        aura = aura,
        cl = cl,
        bonusPrice = bonusPrice,
        moneyPrice = moneyPrice,
        description = description,
        constructionRequirements = constructionRequirements,
        bookAlias = book?.alias?.value,
        bookName = book?.name,
        bookOrder = book?.order,
        bookAbbreviation = book?.abbreviation,
    )

    private fun WeaponAbilityEntity.toDomain(): WeaponAbility {
        val bookResponse = bookAlias
            ?.let { aliasStr -> BookAlias.entries.find { entry -> entry.value == aliasStr } }
            ?.let { bookAliasEnum ->
                Book(
                    alias = bookAliasEnum,
                    name = bookName.orEmpty(),
                    order = bookOrder ?: 0,
                    abbreviation = bookAbbreviation.orEmpty(),
                )
            }
        return WeaponAbility(
            alias = alias,
            name = name,
            engName = engName,
            aura = aura,
            cl = cl,
            bonusPrice = bonusPrice,
            moneyPrice = moneyPrice,
            description = description,
            constructionRequirements = constructionRequirements,
            book = bookResponse,
        )
    }
}
