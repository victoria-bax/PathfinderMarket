package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.data.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.bonusAbility
import victoria.bakhaeva.pathfindermarket.domain.dictionaries.WeaponBonusPrice.maxBonus
import java.io.IOException
import javax.inject.Inject

internal class WeaponsAbilitiesInteractor @Inject constructor(
    private val pathfinderApi: PathfinderApi,
    private val weaponCache: InMemoryWeaponCache,
) {

    fun getAbilities(range: String): Flow<List<WeaponAbility>> = flow {
        try {
            val query = when (range) {
                Range.MELEE.value -> "meeleWeapon"
                Range.RANGED.value -> "rangedWeapon"
                else -> throw Exception("Unknown range")
            }
            val response = pathfinderApi.getAbilities(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    val abilities = createBonusAbilities() + it
                    weaponCache.saveWeaponAbilities(abilities)
                    emit(abilities)
                } ?: throw Exception("Empty response body")
            } else {
                throw HttpException(response) // Обработка ошибки HTTP
            }
        } catch (e: IOException) {
            // Ошибка сети
            throw IOException("Network error", e)
        } catch (e: HttpException) {
            // Ошибка HTTP
            throw HttpException(e.response())
        } catch (e: Exception) {
            // Обработка других ошибок
            throw Exception("An unexpected error occurred", e)
        }
    }

    private fun createBonusAbilities(): List<WeaponAbility> =
        List(maxBonus) { bonus ->
            bonusAbility(bonus + 1)
        }

    fun getAbility(abilityAlias: String): WeaponAbility? =
        weaponCache.getWeaponAbility(abilityAlias)

}
