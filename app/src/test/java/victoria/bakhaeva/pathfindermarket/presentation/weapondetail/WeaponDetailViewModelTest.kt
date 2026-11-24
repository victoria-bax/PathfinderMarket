package victoria.bakhaeva.pathfindermarket.presentation.weapondetail

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
import org.junit.Before
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.makeWeapon

@OptIn(ExperimentalCoroutinesApi::class)
class WeaponDetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val interactor: WeaponsInteractor = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(alias: String = "sword") = WeaponDetailViewModel(
        getWeaponsInteractor = interactor,
        savedStateHandle = SavedStateHandle(mapOf("alias" to alias)),
    )

    @Test
    fun `emits Loading then Success when weapon found`() = runTest {
        val weapon = makeWeapon()
        coEvery { interactor.getWeapon("sword") } returns weapon

        val vm = viewModel()
        assertEquals(UIState.Loading, vm.uiState.value)

        advanceUntilIdle()
        assertEquals(UIState.Success(weapon), vm.uiState.value)
    }

    @Test
    fun `emits Error(error_weapon_not_found) when weapon is null`() = runTest {
        coEvery { interactor.getWeapon("sword") } returns null

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_weapon_not_found), vm.uiState.value)
    }

    @Test
    fun `emits Error(error_generic) on exception`() = runTest {
        coEvery { interactor.getWeapon("sword") } throws RuntimeException("network error")

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_generic), vm.uiState.value)
    }
}
