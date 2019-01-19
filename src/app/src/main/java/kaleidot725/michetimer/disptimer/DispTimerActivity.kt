package kaleidot725.michetimer.disptimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerActivity

class DispTimerActivity : AppCompatActivity() {

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
    }
}
