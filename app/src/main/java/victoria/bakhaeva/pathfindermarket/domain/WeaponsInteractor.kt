package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import victoria.bakhaeva.pathfindermarket.data.FilterPreferencesRepository
import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.presentation.model.WeaponListItemUiState
import victoria.bakhaeva.pathfindermarket.ui.weaponList.Order
import javax.inject.Inject

internal class WeaponsInteractor @Inject constructor(
    private val weaponCache: WeaponCache,
    private val filterPrefs: FilterPreferencesRepository,
) {

    fun getWeapons(): Flow<List<Weapon>> = flow {
        weaponCache.getWeapons()?.let { emit(it) }
    }

    suspend fun getWeapon(alias: String): Weapon? = weaponCache.getWeapon(alias)

    suspend fun searchWeapons(query: String, sort: Order): List<Weapon> =
        weaponCache.searchWeapons(query).sort(sort)

    suspend fun filterWeapons(
        allFilters: AllFilters,
        selectedAliases: List<String>,
        sort: Order,
    ): List<Weapon> {
        val selectedSet = selectedAliases.toSet()
        return weaponCache.filterWeapons(
            rangeAliases = allFilters.rangeCategories.filter { it.alias.value in selectedSet }.map { it.alias }.toSet(),
            proficientAliases = allFilters.proficientCategories.filter { it.alias.value in selectedSet }.map { it.alias }.toSet(),
            encumbranceAliases = allFilters.encumbranceCategories.filter { it.alias.value in selectedSet }.map { it.alias }.toSet(),
            bookAliases = allFilters.books.filter { it.alias.value in selectedSet }.map { it.alias }.toSet(),
        ).sort(sort)
    }

    fun sortItems(items: List<WeaponListItemUiState>, sort: Order): List<WeaponListItemUiState> =
        when (sort) {
            Order.PRICE_ASC -> items.sortedBy { it.cost ?: Int.MAX_VALUE }
            Order.PRICE_DESC -> items.sortedByDescending { it.cost ?: Int.MIN_VALUE }
            Order.NAME_ASC -> items.sortedBy { it.name }
            Order.NAME_DESC -> items.sortedByDescending { it.name }
        }

    suspend fun saveFilters(selectedAliases: List<String>) =
        filterPrefs.saveSelectedFilters(selectedAliases)

    suspend fun loadFilters(): List<String> = filterPrefs.loadSelectedFilters()

    private fun List<Weapon>.sort(sort: Order): List<Weapon> = when (sort) {
        Order.PRICE_ASC -> sortedBy { it.cost }
        Order.PRICE_DESC -> sortedByDescending { it.cost }
        Order.NAME_ASC -> sortedBy { it.name }
        Order.NAME_DESC -> sortedByDescending { it.name }
    }
}
