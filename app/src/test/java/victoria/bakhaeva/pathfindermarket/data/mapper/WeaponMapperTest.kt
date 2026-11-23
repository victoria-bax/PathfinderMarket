package victoria.bakhaeva.pathfindermarket.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.data.model.ProficientCategoryResponse
import victoria.bakhaeva.pathfindermarket.data.model.RangeCategoryResponse
import victoria.bakhaeva.pathfindermarket.data.model.WeaponResponse
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias

class WeaponMapperTest {

    private val mapper = WeaponMapper()

    private fun minimalResponse(
        alias: String = "test",
        description: String? = null,
        damageS: String? = null,
        damageM: String? = null,
        criticalRoll: String? = null,
        criticalDamage: String? = null,
        range: String? = null,
        special: String? = null,
        cost: Double? = null,
    ) = WeaponResponse(
        alias = alias,
        name = "Test",
        description = description,
        damageS = damageS,
        damageM = damageM,
        criticalRoll = criticalRoll,
        criticalDamage = criticalDamage,
        range = range,
        special = special,
        cost = cost,
        proficientCategory = ProficientCategoryResponse(alias = ProficientAlias.MARTIAL),
        rangeCategory = RangeCategoryResponse(alias = RangeAlias.MELEE),
    )

    // --- stripHtml ---

    @Test
    fun `stripHtml removes anchor tags from description`() {
        val response = minimalResponse(
            description = """Вы получаете бонус +2 к проверкам <a href='/skill/sleightOfHand' class='link'>Ловкости Рук</a>, сделанным, чтобы скрыть пистолет."""
        )
        val weapon = mapper.map(response)
        assertEquals(
            "Вы получаете бонус +2 к проверкам Ловкости Рук, сделанным, чтобы скрыть пистолет.",
            weapon.description
        )
    }

    @Test
    fun `stripHtml removes all html tags`() {
        val result = "<b>bold</b> and <i>italic</i>".stripHtml()
        assertEquals("bold and italic", result)
    }

    @Test
    fun `stripHtml returns null for null input`() {
        assertNull(null.stripHtml())
    }

    @Test
    fun `stripHtml returns plain string unchanged`() {
        assertEquals("plain text", "plain text".stripHtml())
    }

    @Test
    fun `stripHtml removes self-closing tags`() {
        assertEquals("line1line2", "line1<br/>line2".stripHtml())
    }

    // --- cost conversion ---

    @Test
    fun `cost converts gold to copper - whole number`() {
        val weapon = mapper.map(minimalResponse(cost = 15.0))
        assertEquals(1500, weapon.cost)
    }

    @Test
    fun `cost converts gold to copper - fractional 0_1`() {
        val weapon = mapper.map(minimalResponse(cost = 0.1))
        assertEquals(10, weapon.cost)
    }

    @Test
    fun `cost converts gold to copper - fractional 0_5`() {
        val weapon = mapper.map(minimalResponse(cost = 0.5))
        assertEquals(50, weapon.cost)
    }

    @Test
    fun `cost is null when not provided`() {
        val weapon = mapper.map(minimalResponse(cost = null))
        assertNull(weapon.cost)
    }

    // --- description passes through stripHtml ---

    @Test
    fun `description without html is unchanged`() {
        val weapon = mapper.map(minimalResponse(description = "Обычное описание."))
        assertEquals("Обычное описание.", weapon.description)
    }

    @Test
    fun `null description stays null`() {
        val weapon = mapper.map(minimalResponse(description = null))
        assertNull(weapon.description)
    }
}
