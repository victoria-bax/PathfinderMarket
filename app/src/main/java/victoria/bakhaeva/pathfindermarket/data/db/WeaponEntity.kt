package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weapons")
data class WeaponEntity(
    @PrimaryKey val alias: String,
    val json: String,
)
