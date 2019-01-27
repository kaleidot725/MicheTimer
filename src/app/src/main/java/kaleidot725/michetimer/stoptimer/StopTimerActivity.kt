package kaleidot725.michetimer.stoptimer

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kaleidot725.michetimer.R
import kaleidot725.michetimer.domain.TimerRunnerState
import kaleidot725.michetimer.timerService
import java.util.*

class StopTimerActivity : AppCompatActivity()  {

    companion object {
        fun create(context : Context, id : Int, start : Long, end : Long) : Intent {
            val intent = Intent(context, StopTimerActivity::class.java).apply {
                putExtra("id", id)
                putExtra("start", start)
                putExtra( "end", end)
            }

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize activity property
        val id = intent.getIntExtra("id", -1)
        val start = Date(intent.getLongExtra("start", -1))
        val end = Date(intent.getLongExtra("end", -1))

        var controller = timerService.resolve(id)
        if (controller.state.value == TimerRunnerState.Timeout &&
            controller.start.equals(start) &&
            controller.end.equals(end))
        {
            controller.reset()
        }
        timerService.unregister(id)

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun 
}
