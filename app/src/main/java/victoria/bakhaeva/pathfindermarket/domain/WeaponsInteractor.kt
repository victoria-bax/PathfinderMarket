package victoria.bakhaeva.pathfindermarket.domain

import victoria.bakhaeva.pathfindermarket.data.FilterPreferencesRepository
import victoria.bakhaeva.pathfindermarket.domain.model.AllFilters
import victoria.bakhaeva.pathfindermarket.domain.model.Weapon
import victoria.bakhaeva.pathfindermarket.domain.model.Order
import javax.inject.Inject

internal class WeaponsInteractor @Inject constructor(
    private val weaponCache: WeaponCache,
    private val filterPrefs: FilterPreferencesRepository,
) {

    suspend fun getWeapons(): List<Weapon>? = weaponCache.getWeapons()

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

    suspend fun saveFilters(selectedAliases: List<String>) =
        filterPrefs.saveSelectedFilters(selectedAliases)

    suspend fun loadFilters(): List<String> = filterPrefs.loadSelectedFilters()

    private fun List<Weapon>.sort(sort: Order): List<Weapon> = when (sort) {
        Order.PRICE_ASC -> sortedBy { it.cost ?: Int.MAX_VALUE }
        Order.PRICE_DESC -> sortedByDescending { it.cost ?: Int.MIN_VALUE }
        Order.NAME_ASC -> sortedBy { it.name }
        Order.NAME_DESC -> sortedByDescending { it.name }
    }
}

