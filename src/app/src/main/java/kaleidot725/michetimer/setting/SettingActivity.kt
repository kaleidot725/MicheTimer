package kaleidot725.michetimer.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingFragment.newInstance())
                    .commitNow()
        }
    }

}
