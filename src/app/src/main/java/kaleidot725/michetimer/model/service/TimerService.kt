package kaleidot725.michetimer.model.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kaleidot725.michetimer.R
import android.app.PendingIntent
import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kaleidot725.michetimer.model.domain.alarm.AlarmPlayer
import kaleidot725.michetimer.model.domain.alarm.AlarmPlayerDefault
import kaleidot725.michetimer.model.domain.timer.*
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import java.lang.Exception
import java.text.SimpleDateFormat


class TimerService(context : Context)  {
    private val tag: String = "TimerService"
    private val runners : MutableMap<Int, TimerRunner> = mutableMapOf()
    private val players : MutableMap<Int, AlarmPlayer> = mutableMapOf()
    private val disposes : MutableMap<Int, CompositeDisposable> = mutableMapOf()
    private val callbacks : MutableList<TimerCallback> = mutableListOf()

    private val context : Context = context

    fun dispose() {
        runners.forEach {
            it.value.dispose()
        }
        runners.clear()

        players.forEach {
            it.value.dispose()
        }
        players.clear()
    }

    fun register(id : Int, name : String, seconds : Long, alarm : Int): TimerIndicator {
        if (runners[id] != null)
        {
            return runners[id] as TimerIndicator
        }

        players[id] = AlarmPlayerDefault(context, alarm, true)
        runners[id] = TimerRunnerDefault(id, name, seconds)
        disposes[id] = CompositeDisposable()

        val dispose = runners[id]?.state?.subscribe {
            onStateChanged(runners[id] as TimerRunner, players[id] as AlarmPlayer)
        }
        disposes[id]!!.add(dispose as Disposable)

        Log.v(tag, "register ${id} ${name} ${seconds} ${alarm}")
        return runners[id] as TimerIndicator
    }

    fun resolve(id: Int): TimerIndicator {
        if (runners[id] == null)
            throw IndexOutOfBoundsException()

        Log.v(tag, "register ${id} ${runners[id]?.name} ${runners[id]?.seconds} ${players[id]?.type}")
        return runners[id] as TimerIndicator
    }

    fun unregister(id : Int) {
        runners[id]?.dispose()
        runners.remove(id)

        players[id]?.dispose()
        players.remove(id)

        Log.v(tag, "register ${id}")
        return
    }

    fun addNotificationCallback(callback : TimerCallback) {
        callbacks.add(callback)
    }

    fun removeNotificationCallback(callback: TimerCallback) {
        callbacks.remove(callback)
    }

    private fun onStateChanged(runner : TimerRunner, alarm : AlarmPlayer) {
        when (runner.state.value) {
            TimerRunnerState.Init -> {
                alarm.stop()
            }
            TimerRunnerState.Timeout -> {
                alarm.play()
            }
            TimerRunnerState.Run -> {

            }
            TimerRunnerState.Pause -> {

            }
        }

        callbacks.forEach{
            try {
                it.notify(runner as TimerIndicator)
            }
            catch(e : Exception) {

            }
        }
    }
}
