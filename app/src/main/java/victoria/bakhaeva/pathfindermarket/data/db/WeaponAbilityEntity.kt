package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weapon_abilities")
data class WeaponAbilityEntity(
    @PrimaryKey val alias: String,
    val json: String,
)
