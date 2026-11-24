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
class WeaponAbilitiesViewModelTest {

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

    private fun viewModel(range: String = "melee") = WeaponAbilitiesViewModel(
        abilitiesInteractor = interactor,
        savedStateHandle = SavedStateHandle(mapOf("range" to range)),
    )

    @Test
    fun `emits Success with abilities list`() = runTest {
        val abilities = listOf(makeAbility("flaming"), makeAbility("frost"))
        coEvery { interactor.getAbilities("melee") } returns abilities

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Success(abilities), vm.uiState.value)
    }

    @Test
    fun `emits Success with empty list when null returned`() = runTest {
        coEvery { interactor.getAbilities("melee") } returns null

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Success(emptyList<victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility>()), vm.uiState.value)
    }

    @Test
    fun `emits Error on exception`() = runTest {
        coEvery { interactor.getAbilities("melee") } throws RuntimeException("failed")

        val vm = viewModel()
        advanceUntilIdle()

        assertEquals(UIState.Error(R.string.error_generic), vm.uiState.value)
    }
}
