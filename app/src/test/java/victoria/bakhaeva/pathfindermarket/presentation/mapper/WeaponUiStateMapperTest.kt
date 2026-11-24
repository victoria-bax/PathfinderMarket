package victoria.bakhaeva.pathfindermarket.presentation.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.domain.model.Book
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceCategory
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import victoria.bakhaeva.pathfindermarket.presentation.makeWeapon

class WeaponUiStateMapperTest {

    private val mapper = WeaponUiStateMapper()

    @Test
    fun `map empty list returns empty weapons`() {
        val result = mapper.map(emptyList())
        assertEquals(0, result.weapons.size)
    }

    @Test
    fun `map preserves alias and name`() {
        val weapon = makeWeapon(alias = "sword", name = "Long Sword")
        val result = mapper.map(listOf(weapon))
        assertEquals("sword", result.weapons[0].alias)
        assertEquals("Long Sword", result.weapons[0].name)
    }

    @Test
    fun `map preserves cost`() {
        val weapon = makeWeapon(cost = 1500)
        val result = mapper.map(listOf(weapon))
        assertEquals(1500, result.weapons[0].cost)
    }

    @Test
    fun `map null cost stays null`() {
        val weapon = makeWeapon(cost = null)
        val result = mapper.map(listOf(weapon))
        assertNull(result.weapons[0].cost)
    }

    @Test
    fun `map melee weapon has melee icon`() {
        val weapon = makeWeapon(rangeAlias = RangeAlias.MELEE)
        val result = mapper.map(listOf(weapon))
        assertEquals(victoria.bakhaeva.pathfindermarket.R.drawable.ic_melee, result.weapons[0].rangeIconRes)
    }

    @Test
    fun `map ranged weapon has ranged icon`() {
        val weapon = makeWeapon(rangeAlias = RangeAlias.RANGED)
        val result = mapper.map(listOf(weapon))
        assertEquals(victoria.bakhaeva.pathfindermarket.R.drawable.ic_ranged, result.weapons[0].rangeIconRes)
    }

    @Test
    fun `map extracts unique range categories for filter`() {
        val w1 = makeWeapon("s1", rangeAlias = RangeAlias.MELEE)
        val w2 = makeWeapon("s2", rangeAlias = RangeAlias.MELEE)
        val w3 = makeWeapon("s3", rangeAlias = RangeAlias.RANGED)
        val result = mapper.map(listOf(w1, w2, w3))
        assertEquals(2, result.filterState.allFilters.rangeCategories.size)
    }

    @Test
    fun `map extracts unique proficient categories for filter`() {
        val w1 = makeWeapon("s1", proficientAlias = ProficientAlias.MARTIAL)
        val w2 = makeWeapon("s2", proficientAlias = ProficientAlias.MARTIAL)
        val w3 = makeWeapon("s3", proficientAlias = ProficientAlias.SIMPLE)
        val result = mapper.map(listOf(w1, w2, w3))
        assertEquals(2, result.filterState.allFilters.proficientCategories.size)
    }

    @Test
    fun `map starts with no selected filters`() {
        val weapon = makeWeapon()
        val result = mapper.map(listOf(weapon))
        assertEquals(emptyList<String>(), result.filterState.selectedFilters)
    }

    @Test
    fun `toUiStates maps list correctly`() {
        val weapons = listOf(makeWeapon("a"), makeWeapon("b"))
        val result = with(mapper) { weapons.toUiStates() }
        assertEquals(2, result.size)
        assertEquals("a", result[0].alias)
        assertEquals("b", result[1].alias)
    }
}
