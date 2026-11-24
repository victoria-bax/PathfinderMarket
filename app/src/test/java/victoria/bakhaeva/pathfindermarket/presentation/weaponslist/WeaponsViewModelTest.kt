package victoria.bakhaeva.pathfindermarket.presentation.weaponslist

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.Before
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.domain.model.Order
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.makeWeapon
import victoria.bakhaeva.pathfindermarket.presentation.mapper.WeaponUiStateMapper
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListUiState

@OptIn(ExperimentalCoroutinesApi::class)
class WeaponsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val interactor: WeaponsInteractor = mockk()
    private val mapper = WeaponUiStateMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel() = WeaponsViewModel(interactor = interactor, mapper = mapper)

    @Test
    fun `emits Loading then Success on load`() = runTest {
        val weapons = listOf(makeWeapon("sword"), makeWeapon("axe", "Axe"))
        coEvery { interactor.getWeapons() } returns weapons
        coEvery { interactor.loadFilters() } returns emptyList()

        val vm = viewModel()
        assertEquals(UIState.Loading, vm.uiState.value)

        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(2, state.weapons.size)
    }

    @Test
    fun `emits Success with empty list when getWeapons returns null`() = runTest {
        coEvery { interactor.getWeapons() } returns null
        coEvery { interactor.loadFilters() } returns emptyList()

        val vm = viewModel()
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(0, state.weapons.size)
    }

    @Test
    fun `emits Error on exception`() = runTest {
        coEvery { interactor.getWeapons() } throws RuntimeException("db error")
        coEvery { interactor.loadFilters() } returns emptyList()

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_generic), vm.uiState.value)
    }

    @Test
    fun `onSortSelected updates sort in state`() = runTest {
        val weapons = listOf(makeWeapon())
        coEvery { interactor.getWeapons() } returns weapons
        coEvery { interactor.loadFilters() } returns emptyList()
        coEvery { interactor.filterWeapons(any(), any(), any()) } returns weapons

        val vm = viewModel()
        advanceUntilIdle()

        vm.onSortSelected(Order.NAME_ASC)
        advanceUntilIdle()

        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(Order.NAME_ASC, state.sort.selected)
    }

    @Test
    fun `onSearch calls interactor searchWeapons`() = runTest {
        val weapons = listOf(makeWeapon())
        coEvery { interactor.getWeapons() } returns weapons
        coEvery { interactor.loadFilters() } returns emptyList()
        coEvery { interactor.searchWeapons("sword", any()) } returns weapons

        val vm = viewModel()
        advanceUntilIdle()

        vm.onSearch("sword")
        advanceUntilIdle()

        coVerify { interactor.searchWeapons("sword", any()) }
    }

    @Test
    fun `onSearch with empty query reapplies filters`() = runTest {
        val weapons = listOf(makeWeapon())
        coEvery { interactor.getWeapons() } returns weapons
        coEvery { interactor.loadFilters() } returns emptyList()
        coEvery { interactor.filterWeapons(any(), any(), any()) } returns weapons

        val vm = viewModel()
        advanceUntilIdle()

        vm.onSearch("")
        advanceUntilIdle()

        coVerify { interactor.filterWeapons(any(), any(), any()) }
    }

    @Test
    fun `onFilterApplied saves filters and updates state`() = runTest {
        val weapons = listOf(makeWeapon())
        coEvery { interactor.getWeapons() } returns weapons
        coEvery { interactor.loadFilters() } returns emptyList()
        coEvery { interactor.saveFilters(any()) } returns Unit
        coEvery { interactor.filterWeapons(any(), any(), any()) } returns weapons

        val vm = viewModel()
        advanceUntilIdle()

        vm.onFilterApplied(listOf("melee"))
        advanceUntilIdle()

        coVerify { interactor.saveFilters(listOf("melee")) }
        val state = (vm.uiState.value as UIState.Success).data
        assertEquals(listOf("melee"), state.filterState.selectedFilters)
    }
}
