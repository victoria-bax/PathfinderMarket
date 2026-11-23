package victoria.bakhaeva.pathfindermarket.ui.weaponList

import androidx.annotation.DrawableRes
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias

enum class Range(@DrawableRes val icon: Int) {
    MELEE(R.drawable.ic_melee),
    RANGED(R.drawable.ic_ranged);

    companion object {
        fun from(alias: RangeAlias): Range = when (alias) {
            RangeAlias.MELEE -> MELEE
            RangeAlias.RANGED -> RANGED
        }
    }
}
