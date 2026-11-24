package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val alias: String,
    val name: String,
    val order: Int,
    val abbreviation: String,
)

@Entity(
    tableName = "weapons",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["alias"],
            childColumns = ["bookAlias"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("bookAlias")],
)
data class WeaponEntity(
    @PrimaryKey val alias: String,
    val name: String,
    val engName: String?,
    val type: String?,
    val cost: Int?,
    val damageS: String?,
    val damageM: String?,
    val criticalRoll: String?,
    val criticalDamage: String?,
    val range: String?,
    val misfire: String?,
    val capacity: String?,
    val weight: Double?,
    val special: String?,
    val description: String?,
    val proficientCategoryAlias: String,
    val proficientCategoryName: String?,
    val rangeCategoryAlias: String,
    val rangeCategoryName: String?,
    val encumbranceCategoryAlias: String?,
    val encumbranceCategoryName: String?,
    val bookAlias: String?,
)

@Entity(
    tableName = "weapon_parents",
    primaryKeys = ["weaponAlias", "parentName"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["alias"],
            childColumns = ["weaponAlias"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("weaponAlias")],
)
data class WeaponParentEntity(
    val weaponAlias: String,
    val parentName: String,
)

@Entity(
    tableName = "weapon_children",
    primaryKeys = ["parentAlias", "childAlias"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["alias"],
            childColumns = ["parentAlias"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["alias"],
            childColumns = ["childAlias"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("parentAlias"), Index("childAlias")],
)
data class WeaponChildEntity(
    val parentAlias: String,
    val childAlias: String,
)
