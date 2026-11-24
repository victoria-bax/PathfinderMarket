package victoria.bakhaeva.pathfindermarket.presentation.weaponupgrade

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.makeAbility
import victoria.bakhaeva.pathfindermarket.presentation.makeWeapon

@OptIn(ExperimentalCoroutinesApi::class)
class WeaponUpgradeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val weaponsInteractor: WeaponsInteractor = mockk()
    private val abilitiesInteractor: WeaponsAbilitiesInteractor = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(alias: String = "sword") = WeaponUpgradeViewModel(
        getWeaponsInteractor = weaponsInteractor,
        abilitiesInteractor = abilitiesInteractor,
        savedStateHandle = SavedStateHandle(mapOf("alias" to alias)),
    )

    @Test
    fun `emits Success with empty abilities on load`() = runTest {
        val weapon = makeWeapon()
        coEvery { weaponsInteractor.getWeapon("sword") } returns weapon

        val vm = viewModel()
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(weapon, state.weapon)
        assertTrue(state.abilities.isEmpty())
        assertEquals(false, state.isMasterwork)
    }

    @Test
    fun `emits Error when weapon not found`() = runTest {
        coEvery { weaponsInteractor.getWeapon("sword") } returns null

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_weapon_not_found), vm.uiState.value)
    }

    @Test
    fun `emits Error on exception`() = runTest {
        coEvery { weaponsInteractor.getWeapon("sword") } throws RuntimeException()

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_generic), vm.uiState.value)
    }

    @Test
    fun `onMasterworkChange updates isMasterwork`() = runTest {
        coEvery { weaponsInteractor.getWeapon("sword") } returns makeWeapon()

        val vm = viewModel()
        advanceUntilIdle()

        vm.onMasterworkChange(true)

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(true, state.isMasterwork)
    }

    @Test
    fun `onImprovementClicked updates improvements`() = runTest {
        coEvery { weaponsInteractor.getWeapon("sword") } returns makeWeapon()

        val vm = viewModel()
        advanceUntilIdle()

        vm.onImprovementClicked(3)

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(3, state.improvements)
    }

    @Test
    fun `addAbility appends ability`() = runTest {
        val weapon = makeWeapon()
        val ability = makeAbility()
        coEvery { weaponsInteractor.getWeapon("sword") } returns weapon
        coEvery { abilitiesInteractor.getAbility("flaming") } returns ability

        val vm = viewModel()
        advanceUntilIdle()

        vm.addAbility("flaming")
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(listOf(ability), state.abilities)
    }

    @Test
    fun `addAbility does not duplicate existing ability`() = runTest {
        val weapon = makeWeapon()
        val ability = makeAbility()
        coEvery { weaponsInteractor.getWeapon("sword") } returns weapon
        coEvery { abilitiesInteractor.getAbility("flaming") } returns ability

        val vm = viewModel()
        advanceUntilIdle()

        vm.addAbility("flaming")
        advanceUntilIdle()
        vm.addAbility("flaming")
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(1, state.abilities.size)
    }

    @Test
    fun `ability result via savedStateHandle triggers addAbility`() = runTest {
        val weapon = makeWeapon()
        val ability = makeAbility()
        coEvery { weaponsInteractor.getWeapon("sword") } returns weapon
        coEvery { abilitiesInteractor.getAbility("flaming") } returns ability

        val savedStateHandle = SavedStateHandle(mapOf("alias" to "sword"))
        val vm = WeaponUpgradeViewModel(weaponsInteractor, abilitiesInteractor, savedStateHandle)
        advanceUntilIdle()

        savedStateHandle[WeaponUpgradeViewModel.ABILITY_RESULT_KEY] = "flaming"
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(listOf(ability), state.abilities)
    }
}
