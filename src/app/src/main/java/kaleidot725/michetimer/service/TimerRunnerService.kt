package kaleidot725.michetimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kaleidot725.michetimer.R
import java.lang.Exception
import android.app.PendingIntent
import kaleidot725.michetimer.timerstop.StopTimerActivity


class TimerRunnerService : Service(), TimerRunnerServiceInterface {
    private val tag: String = "TimerService"
    private val binder: IBinder = ServiceBinder()
    private val runners : MutableMap<Int, TimerRunnerInterface> = mutableMapOf()
    private val players : MutableMap<Int, TimerMediaPlayerInterface> = mutableMapOf()

    private lateinit var builder : NotificationCompat.Builder
    private lateinit var manager : NotificationManager

    inner class ServiceBinder : Binder() {
        val instance: TimerRunnerService get() = this@TimerRunnerService
    }

    override fun onCreate() {
        Log.v(tag, "onCreate")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("ID", "NAME",  NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "DESCRIPTION TEXT"

            val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        builder = NotificationCompat.Builder(applicationContext, "ID")
        manager = applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.v(tag, "onBind")
        return this.binder
    }

    override fun register(id : Int, name : String, seconds : Long, sound : String): TimerRunnerController {
        try {
            if (!runners.containsKey(id)) {
                players[id] = TimerMediaPlayer(applicationContext, sound)
                runners[id] = TimerRunner(applicationContext, id, name, seconds).apply {
                    state.subscribe {
                        when (runners[id]?.state?.value) {
                            TimerRunnerState.Init -> {
                                players[id]?.stop()
                            }
                            TimerRunnerState.Timeout -> {
                                players[id]?.play()
                                notificaton(id)
                            }
                            TimerRunnerState.Run -> {
                                // undefined
                            }
                            TimerRunnerState.Pause -> {
                                // undefined
                            }
                        }

                    }
                }
            }

            return runners[id] as TimerRunnerController
        }
        catch (e : Exception) {
            Log.v(tag, "register error ${id} ${name} ${seconds} ${sound}")
            throw e
        }
    }

    private fun notificaton(id : Int)
    {
        val intent = Intent(this, StopTimerActivity::class.java)
        intent.putExtra("id", id)
        val pIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setTicker(runners[id]?.name + " is time up")
                .setContentTitle(runners[id]?.name + " is time up")
                .setContentText("Click to stop alarm!!")
                .setContentIntent(pIntent)

        manager.notify(id , builder.build())
    }

    override fun resolve(id: Int): TimerRunnerController {
        return runners[id] as TimerRunnerController
    }

    override fun unregister(id : Int) {
        try {
            runners[id]?.finalize()
            runners.remove(id)

            players[id]?.finalize()
            players.remove(id)
        }
        catch (e: Exception)
        {
            Log.v(tag, "Unregister ${id}")
            throw e
        }
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

        super.onDestroy()
    }
}
