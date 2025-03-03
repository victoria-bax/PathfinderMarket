package victoria.bakhaeva.pathfindermarket.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import victoria.bakhaeva.pathfindermarket.data.model.Ability
import victoria.bakhaeva.pathfindermarket.data.model.Weapon

internal interface PathfinderApi {
    @GET("weapons")
    suspend fun getWeapons(): Response<List<Weapon>>

    @GET("magicItemAbilities")
    suspend fun getAbilities(@Query("types") types: String): Response<List<Ability>>
}