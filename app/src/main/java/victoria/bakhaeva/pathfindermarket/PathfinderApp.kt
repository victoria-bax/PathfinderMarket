package victoria.bakhaeva.pathfindermarket

import android.app.Application
import com.google.ai.client.generativeai.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PathfinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //do some Crashlytics or other crash reporting logs
        }
    }
}