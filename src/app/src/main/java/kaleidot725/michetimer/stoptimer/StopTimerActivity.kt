package kaleidot725.michetimer.stoptimer

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kaleidot725.michetimer.R
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import kaleidot725.michetimer.domain.TimerRunnerState
import java.util.*
import javax.inject.Inject

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

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerRunnerService : TimerRunnerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MicheTimerApplication).component.inject(this)

        // Initialize activity property
        val id = intent.getIntExtra("id", -1)
        val start = Date(intent.getLongExtra("start", -1))
        val end = Date(intent.getLongExtra("end", -1))

        var controller = timerRunnerService.resolve(id)
        if (controller.state.value == TimerRunnerState.Timeout &&
            controller.start.equals(start) &&
            controller.end.equals(end))
        {
            controller.reset()
        }
        timerRunnerService.unregister(id)

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
