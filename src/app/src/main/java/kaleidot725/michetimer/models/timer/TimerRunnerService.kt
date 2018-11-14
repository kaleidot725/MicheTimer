package kaleidot725.michetimer.models.timer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.lang.Exception

class TimerRunnerService : Service(), TimerRunnerServiceInterface {
    private val tag: String = "TimerService"
    private val binder: IBinder = ServiceBinder()
    private val runners: MutableMap<Int, TimerRunnerInterface> = mutableMapOf()

    inner class ServiceBinder : Binder() {
        val instance: TimerRunnerService get() = this@TimerRunnerService
    }

    override fun onCreate() {
        Log.v(tag, "onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.v(tag, "onBind")
        return this.binder
    }

    override fun register(timer: Timer): TimerRunnerInterface {
        try {
            var runner : TimerRunnerInterface
            if (runners[timer.id] == null) {
                runner = TimerRunner(applicationContext, timer.id, timer.name, timer.seconds, timer.sound)
                runners.put(timer.id, runner)
            }
            else {
                runner = runners[timer.id] as TimerRunnerInterface
            }

            return runner
        }
        catch (e : Exception) {
            Log.v(tag, "Unregister ${timer.id} ${timer.name} ${timer.seconds} ${timer.sound}")
            throw e
        }
    }

    override fun unregister(timer : Timer) {
        try {
            val runner = runners.get(timer.id)
            runner?.reset()
            runner?.finalize()
            runners.remove(timer.id)
        }
        catch (e: Exception)
        {
            Log.v(tag, "Unregister ${timer.id} ${timer.name} ${timer.seconds} ${timer.sound}")
            throw e
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(tag, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        runners.forEach {
            it.value.reset()
            it.value.finalize()
        }
        runners.clear()

        super.onDestroy()
    }
}
