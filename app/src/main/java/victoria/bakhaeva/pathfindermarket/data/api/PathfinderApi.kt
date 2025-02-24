package victoria.bakhaeva.pathfindermarket.data.api

import retrofit2.Response
import retrofit2.http.GET
import victoria.bakhaeva.pathfindermarket.data.model.Weapon

internal interface PathfinderApi {
    @GET("weapons")
    suspend fun getWeapons(): Response<List<Weapon>>
}