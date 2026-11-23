package victoria.bakhaeva.pathfindermarket.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private val selectedFiltersKey = stringSetPreferencesKey("selected_filters")

    suspend fun saveSelectedFilters(filters: List<String>) {
        dataStore.edit { it[selectedFiltersKey] = filters.toSet() }
    }

    suspend fun loadSelectedFilters(): List<String> =
        dataStore.data.first()[selectedFiltersKey]?.toList() ?: emptyList()
}
