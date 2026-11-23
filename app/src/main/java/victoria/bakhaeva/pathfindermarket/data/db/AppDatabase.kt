package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeaponEntity::class, WeaponAbilityEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weaponDao(): WeaponDao
    abstract fun weaponAbilityDao(): WeaponAbilityDao
}
