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
import org.junit.Before
import org.junit.Test
import victoria.bakhaeva.pathfindermarket.R
import victoria.bakhaeva.pathfindermarket.domain.WeaponsAbilitiesInteractor
import victoria.bakhaeva.pathfindermarket.presentation.UIState
import victoria.bakhaeva.pathfindermarket.presentation.makeAbility

@OptIn(ExperimentalCoroutinesApi::class)
class WeaponAbilityDetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val interactor: WeaponsAbilitiesInteractor = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(alias: String = "flaming") = WeaponAbilityDetailsViewModel(
        abilitiesInteractor = interactor,
        savedStateHandle = SavedStateHandle(mapOf("alias" to alias)),
    )

    @Test
    fun `emits Success when ability found`() = runTest {
        val ability = makeAbility()
        coEvery { interactor.getAbility("flaming") } returns ability

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Success(ability), vm.uiState.value)
    }

    @Test
    fun `emits Error(error_ability_not_found) when ability is null`() = runTest {
        coEvery { interactor.getAbility("flaming") } returns null

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_ability_not_found), vm.uiState.value)
    }

    @Test
    fun `emits Error(error_generic) on exception`() = runTest {
        coEvery { interactor.getAbility("flaming") } throws RuntimeException("network error")

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_generic), vm.uiState.value)
    }
}
