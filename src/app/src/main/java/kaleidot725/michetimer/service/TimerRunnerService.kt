package kaleidot725.michetimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kaleidot725.michetimer.R
import android.app.PendingIntent
import kaleidot725.michetimer.domain.*
import kaleidot725.michetimer.timerstop.StopTimerActivity
import java.text.SimpleDateFormat


class TimerRunnerService : Service(), TimerRunnerServiceInterface {
    inner class ServiceBinder : Binder() {
        val instance: TimerRunnerService get() = this@TimerRunnerService
    }

    private val tag: String = "TimerService"
    private val binder: IBinder = ServiceBinder()
    private val runners : MutableMap<Int, TimerRunnerInterface> = mutableMapOf()
    private val players : MutableMap<Int, MediaPlayerInterface> = mutableMapOf()

    private lateinit var builder : NotificationCompat.Builder
    private lateinit var manager : NotificationManager

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("ID", "NAME",  NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "DESCRIPTION TEXT"

            val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        builder = NotificationCompat.Builder(applicationContext, "ID")
        manager = applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        Log.v(tag, "onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.v(tag, "onBind")
        return this.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(tag, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        runners.forEach {
            it.value.finalize()
        }
        runners.clear()

        players.forEach {
            it.value.finalize()
        }
        players.clear()

        Log.v(tag, "onDestroy")
        super.onDestroy()
    }

    override fun register(id : Int, name : String, seconds : Long, sound : String): TimerRunnerController {
        if (runners[id] != null)
        {
            return runners[id] as TimerRunnerController
        }

        players[id] = MediaPlayer(applicationContext, sound)
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

        Log.v(tag, "register ${id} ${name} ${seconds} ${sound}")
        return runners[id] as TimerRunnerController
    }

    override fun resolve(id: Int): TimerRunnerController {
        if (runners[id] == null)
            throw IndexOutOfBoundsException()

        Log.v(tag, "register ${id} ${runners[id]?.name} ${runners[id]?.seconds} ${players[id]?.name}")
        return runners[id] as TimerRunnerController
    }

    override fun unregister(id : Int) {
        runners[id]?.finalize()
        runners.remove(id)

        players[id]?.finalize()
        players.remove(id)

        Log.v(tag, "register ${id}")
        return
    }

    private fun notifyTimeOut(runner : TimerRunnerInterface)
    {
        val intent = Intent(this, StopTimerActivity::class.java).apply {
            putExtra("id", runner.id)
            putExtra("start", runner.start.time)
            putExtra( "end", runner.end.time)
        }

        val pIntent = PendingIntent.getActivity(this, runner.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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
