package kaleidot725.michetimer.addtimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kaleidot725.michetimer.R
import kaleidot725.michetimer.models.addTimerNavigator

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        addTimerNavigator = this

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddTimerFragment() as Fragment
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onComplete() {
        this.finish()
    }
}
