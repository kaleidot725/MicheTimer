package kaleidot725.michetimer.stoptimer

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kaleidot725.michetimer.R
import kaleidot725.michetimer.StopTimerActivityModule
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerService
import kaleidot725.michetimer.model.domain.timer.TimerRunnerState
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
    lateinit var timerService : TimerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = (application as MicheTimerApplication).component.plus(StopTimerActivityModule())
        component.inject(this)

        // Initialize activity property
        val id = intent.getIntExtra("id", -1)
        val start = Date(intent.getLongExtra("start", -1))
        val end = Date(intent.getLongExtra("end", -1))

        var controller = timerService.resolve(id)
        if (controller.state.value == TimerRunnerState.Timeout &&
            controller.start == start && controller.end == end) {
            controller.reset()
        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
