package kaleidot725.michetimer.stoptimer

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kaleidot725.michetimer.R
import kaleidot725.michetimer.domain.TimerRunnerState
import kaleidot725.michetimer.service.TimerRunnerService
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

    private inner class TimerServiceConnectionForStoppingTimer : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var timerService = (service as TimerRunnerService.ServiceBinder).instance
            var controller = timerService.resolve(StopTimerActivity@id)
            if (controller.state.value == TimerRunnerState.Timeout &&
                controller.start.equals(StopTimerActivity@start) &&
                controller.end.equals(StopTimerActivity@end))
            {
                controller.reset()
            }

            StopTimerActivity@completed = true
        }

        override fun onServiceDisconnected(name : ComponentName?) {
        }
    }

    private val defaultTimeoutMs = 1000L
    private val defaultIntervalMs = 100L
    private var id : Int = -1
    private var start : Date = Date()
    private var end : Date = Date()
    private var completed : Boolean = false
    private lateinit var connection : TimerServiceConnectionForStoppingTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize activity property
        this.id = intent.getIntExtra("id", -1)
        this.start = Date(intent.getLongExtra("start", -1))
        this.end = Date(intent.getLongExtra("end", -1))

        // Create timer service intent
        val intent = Intent(this, TimerRunnerService::class.java)
        this.connection = this.TimerServiceConnectionForStoppingTimer()
        startService(intent)
        bindService(intent, this.connection, Context.BIND_ADJUST_WITH_ACTIVITY)

        // Wait for stopping timer
        var remainingMs = defaultTimeoutMs
        while(completed) {
            Thread.sleep(defaultIntervalMs)
            remainingMs -= defaultIntervalMs
            if (remainingMs <= 0) {
                break
            }
        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this.connection)
    }
}
