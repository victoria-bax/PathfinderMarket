package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import org.junit.Assert.assertEquals
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.presentation.makeAbility
import victoria.bakhaeva.pathfindermarket.presentation.makeWeapon

class WeaponUpgradeStateTest {

    private fun state(
        cost: Int? = 1500,
        isMasterwork: Boolean = false,
        abilities: List<victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility> = emptyList(),
        improvements: Int = 0,
    ) = WeaponUpgradeState(
        weapon = makeWeapon(cost = cost),
        isMasterwork = isMasterwork,
        abilities = abilities,
        improvements = improvements,
    )

    // countAttackBonus

    @Test
    fun `countAttackBonus returns 0 when no masterwork and no improvements`() {
        assertEquals(0, state().countAttackBonus())
    }

    @Test
    fun `countAttackBonus returns 1 when only masterwork`() {
        assertEquals(1, state(isMasterwork = true).countAttackBonus())
    }

    @Test
    fun `countAttackBonus returns improvements value when improvements set`() {
        assertEquals(3, state(isMasterwork = true, improvements = 3).countAttackBonus())
    }

    // countSum

    @Test
    fun `countSum equals weapon cost when nothing added`() {
        assertEquals(1500, state(cost = 1500).countSum())
    }

    @Test
    fun `countSum is zero when weapon cost is null`() {
        assertEquals(0, state(cost = null).countSum())
    }

    @Test
    fun `countSum adds masterwork price`() {
        val withMasterwork = state(cost = 0, isMasterwork = true).countSum()
        // mastercraftPrice = 30000 (300 gp * 100)
        assertEquals(victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice.mastercraftPrice, withMasterwork)
    }

    @Test
    fun `countSum adds ability moneyPrice`() {
        val ability = makeAbility(moneyPrice = 200000, bonusPrice = null)
        val result = state(cost = 0, isMasterwork = false, abilities = listOf(ability)).countSum()
        assertEquals(200000, result)
    }

    @Test
    fun `countSum sums multiple ability moneyPrices`() {
        val a1 = makeAbility("a1", moneyPrice = 100000, bonusPrice = null)
        val a2 = makeAbility("a2", moneyPrice = 50000, bonusPrice = null)
        val result = state(cost = 0, abilities = listOf(a1, a2)).countSum()
        assertEquals(150000, result)
    }
}
