package victoria.bakhaeva.pathfindermarket.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import victoria.bakhaeva.pathfindermarket.BuildConfig
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.gson.EncumbranceJsonDeserializer
import victoria.bakhaeva.pathfindermarket.data.gson.ProficientJsonDeserializer
import victoria.bakhaeva.pathfindermarket.data.gson.RangeJsonDeserializer
import victoria.bakhaeva.pathfindermarket.data.model.Encumbrance
import victoria.bakhaeva.pathfindermarket.data.model.Proficient
import victoria.bakhaeva.pathfindermarket.data.model.Range
import victoria.bakhaeva.pathfindermarket.domain.InMemoryWeaponCache
import victoria.bakhaeva.pathfindermarket.domain.WeaponCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
//            .registerTypeAdapter(Encumbrance::class.java, EncumbranceJsonDeserializer())
//            .registerTypeAdapter(Proficient::class.java, ProficientJsonDeserializer())
//            .registerTypeAdapter(Range::class.java, RangeJsonDeserializer())
            .create()

    @Provides
    @Singleton
    fun provideWeaponCache(): WeaponCache = InMemoryWeaponCache()

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://pathfinder.family/api/")
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providePathfinderApi(retrofit: Retrofit): PathfinderApi {
        return retrofit.create(PathfinderApi::class.java)
    }
}
