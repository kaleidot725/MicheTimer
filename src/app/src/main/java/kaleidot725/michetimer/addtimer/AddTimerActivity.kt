package kaleidot725.michetimer.addtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addTimerNavigator

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        // FIXME シングルトンでの変数保持をやめる
        addTimerNavigator = this

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddTimerFragment() as Fragment
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onComplete() {
        this.finish()
    }
}
