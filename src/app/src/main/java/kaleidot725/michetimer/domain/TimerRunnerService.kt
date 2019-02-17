package kaleidot725.michetimer.domain

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
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import java.text.SimpleDateFormat


class TimerRunnerService(context : Context)  {
    private val tag: String = "TimerService"
    private val runners : MutableMap<Int, TimerRunnerInterface> = mutableMapOf()
    private val players : MutableMap<Int, AlarmPlayer> = mutableMapOf()
    private val context : Context = context

    private lateinit var builder : NotificationCompat.Builder
    private lateinit var manager : NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("ID", "NAME",  NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "DESCRIPTION TEXT"

            val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        builder = NotificationCompat.Builder(context, "ID")
        manager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun dispose() {
        runners.forEach {
            it.value.finalize()
        }
        runners.clear()

        players.forEach {
            it.value.finalize()
        }
        players.clear()
    }

    fun register(id : Int, name : String, seconds : Long, alarm : Int): TimerRunnerController {
        if (runners[id] != null)
        {
            return runners[id] as TimerRunnerController
        }

        players[id] = AlarmPlayerUsingSoundPool(context, alarm, true)
        runners[id] = TimerRunner(id, name, seconds).apply {
            state.subscribe {
                when (runners[id]?.state?.value) {
                    TimerRunnerState.Init -> {
                        players[id]?.stop()
                    }
                    TimerRunnerState.Timeout -> {
                        players[id]?.play()
                        notifyTimeOut(runners[id] as TimerRunnerInterface)
                    }
                    TimerRunnerState.Run -> {

                    }
                    TimerRunnerState.Pause -> {

                    }
                }
            }
        }

        Log.v(tag, "register ${id} ${name} ${seconds} ${alarm}")
        return runners[id] as TimerRunnerController
    }

    fun resolve(id: Int): TimerRunnerController {
        if (runners[id] == null)
            throw IndexOutOfBoundsException()

        Log.v(tag, "register ${id} ${runners[id]?.name} ${runners[id]?.seconds} ${players[id]?.type}")
        return runners[id] as TimerRunnerController
    }

    fun unregister(id : Int) {
        runners[id]?.finalize()
        runners.remove(id)

        players[id]?.finalize()
        players.remove(id)

        Log.v(tag, "register ${id}")
        return
    }

    private fun notifyTimeOut(runner : TimerRunnerInterface)
    {
        val intent = StopTimerActivity.create(context, runner.id, runner.start.time, runner.end.time)
        val pIntent = PendingIntent.getActivity(context, runner.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val df = SimpleDateFormat("HH:mm:ss")

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setTicker("${runner.name} is Time Up ${df.format(runner.start)} ~ ${df.format(runner.end)}")
                .setContentTitle("${runner.name} is Time Up ${df.format(runner.start)} ~ ${df.format(runner.end)}")
                .setContentText("Click to Stop Alarm!!")
                .setContentIntent(pIntent)

        manager.notify(runner.id , builder.build())
    }
}
