package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BookEntity::class,
        WeaponEntity::class,
        WeaponParentEntity::class,
        WeaponChildEntity::class,
        WeaponAbilityEntity::class,
    ],
    version = 4,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun weaponDao(): WeaponDao
    abstract fun weaponAbilityDao(): WeaponAbilityDao
}
