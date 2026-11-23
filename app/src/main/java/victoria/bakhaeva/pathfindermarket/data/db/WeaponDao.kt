package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(books: List<BookEntity>)

    @Query("SELECT * FROM books WHERE alias = :alias LIMIT 1")
    fun getByAlias(alias: String): BookEntity?
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
