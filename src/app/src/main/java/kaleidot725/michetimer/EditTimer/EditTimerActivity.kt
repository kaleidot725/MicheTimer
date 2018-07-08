package kaleidot725.michetimer.EditTimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kaleidot725.michetimer.MicheTimerFragment
import kaleidot725.michetimer.R

class EditTimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_timer)

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = EditTimerFragment() as Fragment
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
