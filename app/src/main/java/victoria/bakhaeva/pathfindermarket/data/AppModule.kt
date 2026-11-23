package victoria.bakhaeva.pathfindermarket.data

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import victoria.bakhaeva.pathfindermarket.BuildConfig
import victoria.bakhaeva.pathfindermarket.data.api.PathfinderApi
import victoria.bakhaeva.pathfindermarket.data.db.AppDatabase
import victoria.bakhaeva.pathfindermarket.data.db.RoomWeaponCache
import victoria.bakhaeva.pathfindermarket.data.db.WeaponAbilityDao
import victoria.bakhaeva.pathfindermarket.data.db.WeaponDao
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
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://pathfinder.family/api/")
        .client(client)
        .addConverterFactory(converterFactory)
        .build()

    @Provides
    @Singleton
    fun providePathfinderApi(retrofit: Retrofit): PathfinderApi =
        retrofit.create(PathfinderApi::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "pathfinder.db")
            .build()

    @Provides
    fun provideWeaponDao(db: AppDatabase): WeaponDao = db.weaponDao()

    @Provides
    fun provideWeaponAbilityDao(db: AppDatabase): WeaponAbilityDao = db.weaponAbilityDao()

    @Provides
    @Singleton
    fun provideWeaponCache(cache: RoomWeaponCache): WeaponCache = cache
}
