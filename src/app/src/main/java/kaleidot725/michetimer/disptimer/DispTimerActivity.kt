package kaleidot725.michetimer.disptimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.R
import kaleidot725.michetimer.dispTimerNavigator
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService


class DispTimerActivity : AppCompatActivity() , DispTimerNavigator {

    companion object {
        fun create(context : Context, id : Int) : Intent {
            val intent = Intent(context, DispTimerActivity::class.java).apply {
                putExtra("id", id)
            }

            return intent
        }
    }

    private var timerId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disp_timer)

        // FIXME シングルトンでの変数保持をやめる
        dispTimerNavigator = this
        val id = intent.getIntExtra("id", -1)
        val title = timerRepository?.findById(id)?.name
        if (!title.isNullOrEmpty()) {
            setTitle(title)
        }

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = DispTimerFragment.create(this, id)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
