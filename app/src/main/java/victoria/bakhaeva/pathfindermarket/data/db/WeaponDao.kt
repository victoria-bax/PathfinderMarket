package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeaponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weapons: List<WeaponEntity>)

    @Query("SELECT * FROM weapons")
    fun getAll(): List<WeaponEntity>

    @Query("SELECT * FROM weapons WHERE alias = :alias LIMIT 1")
    fun getByAlias(alias: String): WeaponEntity?
}

@Dao
interface WeaponAbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(abilities: List<WeaponAbilityEntity>)

    @Query("SELECT * FROM weapon_abilities")
    fun getAll(): List<WeaponAbilityEntity>

    @Query("SELECT * FROM weapon_abilities WHERE alias = :alias LIMIT 1")
    fun getByAlias(alias: String): WeaponAbilityEntity?

    @Query("SELECT COUNT(*) FROM weapon_abilities")
    fun count(): Int
}
