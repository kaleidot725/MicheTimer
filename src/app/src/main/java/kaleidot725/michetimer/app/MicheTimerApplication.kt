package kaleidot725.michetimer.app

import android.app.*
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kaleidot725.michetimer.DaggerMicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationModule
import kaleidot725.michetimer.model.domain.timer.TimerRunnerState
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerCallback
import kaleidot725.michetimer.model.service.TimerIndicator
import kaleidot725.michetimer.model.service.TimerService
import javax.inject.Inject
import android.graphics.Color
import kaleidot725.michetimer.R
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import java.text.SimpleDateFormat


class MicheTimerApplication : Application() {
    val channelId = "Miche Timer"
    val channelName = "Miche Timer"

    lateinit var component : MicheTimerApplicationComponent
    lateinit var module : MicheTimerApplicationModule

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerService : TimerService

    private lateinit var builder : NotificationCompat.Builder
    private lateinit var manager : NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()
        module  = MicheTimerApplicationModule(this)
        component = DaggerMicheTimerApplicationComponent.builder().micheTimerApplicationModule(module).build()
        component.inject(this)

        createNotificationBuilderAndManager()
        timerService.addNotificationCallback(callback)
    }

    override fun onTerminate() {
        super.onTerminate()

        val service = module.provideTimerService()
        service.dispose()
    }

    fun createNotificationBuilderAndManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.enableVibration(true)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {
            builder = NotificationCompat.Builder(this)
        }
        manager = NotificationManagerCompat.from(applicationContext)
    }

    private val callback = object : TimerCallback {
        override fun notify(indicator: TimerIndicator) {
            when(indicator.state.value) {
                TimerRunnerState.Timeout -> {
                    val intent = StopTimerActivity.create(applicationContext, indicator.id, indicator.start.time, indicator.end.time)
                    val pIntent = PendingIntent.getActivity(applicationContext, indicator.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    builder.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("${indicator.name}")
                            .setContentText("The timer timed up.")
                            .setContentIntent(pIntent)

                    manager.notify(indicator.id, builder.build())
                }
            }
        }
    }
}