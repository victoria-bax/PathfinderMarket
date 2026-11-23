package victoria.bakhaeva.pathfindermarket

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import victoria.bakhaeva.pathfindermarket.data.db.DatabaseSeeder
import javax.inject.Inject

@HiltAndroidApp
class PathfinderApp : Application() {

    @Inject
    internal lateinit var databaseSeeder: DatabaseSeeder

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        databaseSeeder.seedIfEmpty()
    }
}