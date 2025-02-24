package victoria.bakhaeva.pathfindermarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import victoria.bakhaeva.pathfindermarket.ui.main.MainFragment

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}