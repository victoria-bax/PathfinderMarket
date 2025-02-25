package victoria.bakhaeva.pathfindermarket.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.model.Weapon
import java.io.IOException
import javax.inject.Inject

internal class WeaponsInteractor @Inject constructor(
    private val pathfinderApi: PathfinderApi
) {

    fun execute(): Flow<List<Weapon>> = flow {
        try {
            val response = pathfinderApi.getWeapons()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
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
}
