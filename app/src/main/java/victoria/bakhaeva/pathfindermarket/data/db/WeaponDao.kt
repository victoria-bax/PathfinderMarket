package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<BookEntity>)

    @Query("SELECT * FROM books WHERE alias = :alias LIMIT 1")
    fun getByAlias(alias: String): BookEntity?

    @Query("SELECT * FROM books")
    fun getAll(): List<BookEntity>
}

@Dao
interface WeaponDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeapons(weapons: List<WeaponEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParents(parents: List<WeaponParentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChilds(childs: List<WeaponChildEntity>)

    @Query("DELETE FROM weapon_parents")
    fun deleteAllParents()

    @Query("DELETE FROM weapon_childs")
    fun deleteAllChilds()

    @Query("SELECT * FROM weapons")
    fun getAllWeapons(): List<WeaponEntity>

    @Query("SELECT * FROM weapons WHERE alias = :alias LIMIT 1")
    fun getWeaponByAlias(alias: String): WeaponEntity?

    @Query("SELECT parentName FROM weapon_parents WHERE weaponAlias = :alias")
    fun getParents(alias: String): List<String>

    @Query("SELECT w.* FROM weapons w INNER JOIN weapon_childs c ON w.alias = c.childAlias WHERE c.parentAlias = :alias")
    fun getChildWeapons(alias: String): List<WeaponEntity>

    @Query("SELECT * FROM weapon_parents")
    fun getAllParents(): List<WeaponParentEntity>

    @Query("SELECT * FROM weapon_childs")
    fun getAllChildLinks(): List<WeaponChildEntity>

    @Query("SELECT * FROM weapons WHERE name LIKE '%' || :query || '%' OR engName LIKE '%' || :query || '%'")
    fun searchWeapons(query: String): List<WeaponEntity>

    @Query("""
        SELECT * FROM weapons
        WHERE (:rangeCount = 0 OR rangeCategoryAlias IN (:rangeAliases))
        AND (:proficientCount = 0 OR proficientCategoryAlias IN (:proficientAliases))
        AND (:encumbranceCount = 0 OR encumbranceCategoryAlias IN (:encumbranceAliases))
        AND (:bookCount = 0 OR bookAlias IN (:bookAliases))
    """)
    fun filterWeapons(
        rangeAliases: List<String>, rangeCount: Int,
        proficientAliases: List<String>, proficientCount: Int,
        encumbranceAliases: List<String>, encumbranceCount: Int,
        bookAliases: List<String>, bookCount: Int,
    ): List<WeaponEntity>

    @Query("SELECT COUNT(*) FROM weapons")
    fun count(): Int
}

@Dao
interface WeaponAbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(abilities: List<WeaponAbilityEntity>)

    @Query("SELECT * FROM weapon_abilities WHERE rangeType = :rangeType")
    fun getByRangeType(rangeType: String): List<WeaponAbilityEntity>

    @Query("SELECT * FROM weapon_abilities WHERE alias = :alias LIMIT 1")
    fun getByAlias(alias: String): WeaponAbilityEntity?

    @Query("SELECT COUNT(*) FROM weapon_abilities WHERE rangeType = :rangeType")
    fun countByRangeType(rangeType: String): Int
}
