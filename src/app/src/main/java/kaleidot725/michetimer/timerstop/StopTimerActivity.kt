package kaleidot725.michetimer.timerstop

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kaleidot725.michetimer.R
import kaleidot725.michetimer.service.TimerRunnerService
import kaleidot725.michetimer.repository.timerService

class StopTimerActivity : AppCompatActivity()  {
    private val connection : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerService = (service as TimerRunnerService.ServiceBinder).instance
            if (timerService != null) {
                val controller = timerService?.resolve(id)
                controller?.reset()
            }

            completed = true
            Log.v("tag", "onServiceConnected")
        }

        override fun onServiceDisconnected(name : ComponentName?) {
            Log.v("tag", "onServiceDisconnected")
        }
    }

    private var id : Int = -1
    private var completed : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = intent.getIntExtra("id", -1)

        val intent = Intent(this, TimerRunnerService::class.java)
        startService(intent)
        bindService(intent, this.connection, Context.BIND_ADJUST_WITH_ACTIVITY)

        var count = 10
        while(!completed) {
            if (count == 0)
                break
            Thread.sleep(100)
            count--
        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this.connection)
    }
}
