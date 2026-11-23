package victoria.bakhaeva.pathfindermarket.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weapon_abilities",
    indices = [Index("rangeType")],
)
data class WeaponAbilityEntity(
    @PrimaryKey val alias: String,
    val rangeType: String,
    val name: String,
    val engName: String,
    val aura: String,
    val cl: Int,
    val bonusPrice: Int?,
    val moneyPrice: Int?,
    val description: String,
    val constructionRequirements: String,
    val bookAlias: String? = null,
    val bookName: String? = null,
    val bookOrder: Int? = null,
    val bookAbbreviation: String? = null,
)
