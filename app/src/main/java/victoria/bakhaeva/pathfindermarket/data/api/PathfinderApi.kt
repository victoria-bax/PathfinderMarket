package victoria.bakhaeva.pathfindermarket.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import victoria.bakhaeva.pathfindermarket.domain.model.WeaponAbility
import victoria.bakhaeva.pathfindermarket.data.model.WeaponResponse

internal interface PathfinderApi {
    @GET("weapons")
    suspend fun getWeapons(): List<WeaponResponse>

    @GET("magicItemAbilities")
    suspend fun getAbilities(@Query("types") types: String): List<WeaponAbility>
}
