package kaleidot725.michetimer.timerstop

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kaleidot725.michetimer.R
import kaleidot725.michetimer.service.TimerRunnerService
import kaleidot725.michetimer.timerService

class StopTimerActivity : AppCompatActivity()  {
    private inner class TimerServiceConnectionForStoppingTimer : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerService = (service as TimerRunnerService.ServiceBinder).instance
            if (timerService != null) {
                val controller = timerService?.resolve(StopTimerActivity@id)
                controller?.reset()
            }

            StopTimerActivity@completed = true
        }

        override fun onServiceDisconnected(name : ComponentName?) {
        }
    }

    private val defaultTimeoutMs = 1000L
    private val defaultIntervalMs = 100L
    private var id : Int = -1
    private var completed : Boolean = false
    private lateinit var connection : TimerServiceConnectionForStoppingTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize activity property
        this.id = intent.getIntExtra("id", -1)
        this.completed = false

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
