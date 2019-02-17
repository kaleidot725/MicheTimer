package kaleidot725.michetimer.app

import android.app.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kaleidot725.michetimer.DaggerMicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationModule
import kaleidot725.michetimer.R
import kaleidot725.michetimer.model.domain.timer.TimerRunnerState
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerCallback
import kaleidot725.michetimer.model.service.TimerIndicator
import kaleidot725.michetimer.model.service.TimerService
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import java.text.SimpleDateFormat
import javax.inject.Inject

class MicheTimerApplication : Application() {


    lateinit var component : MicheTimerApplicationComponent
    lateinit var module : MicheTimerApplicationModule

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerService : TimerService

    private lateinit var builder : NotificationCompat.Builder
    private lateinit var manager : NotificationManager

    private val callback = object : TimerCallback {
        override fun notify(indicator: TimerIndicator) {
            when(indicator.state.value) {
                TimerRunnerState.Timeout -> {
                    val intent = StopTimerActivity.create(applicationContext, 0, indicator.start.time, indicator.end.time)
                    val pIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    val df = SimpleDateFormat("HH:mm:ss")

                    builder.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.ic_notification)
                            .setTicker("${indicator.name} is Time Up ${df.format(indicator.start)} ~ ${df.format(indicator.end)}")
                            .setContentTitle("${indicator.name} is Time Up ${df.format(indicator.start)} ~ ${df.format(indicator.end)}")
                            .setContentText("Click to Stop Alarm!!")
                            .setContentIntent(pIntent)

                    manager.notify(0 , builder.build())
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        module  = MicheTimerApplicationModule(this)
        component = DaggerMicheTimerApplicationComponent.builder().micheTimerApplicationModule(module).build()
        component.inject(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("ID", "NAME",  NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "DESCRIPTION TEXT"

            val notificationManager = applicationContext.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        builder = NotificationCompat.Builder(applicationContext, "ID")
        manager = applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        timerService.addNotificationCallback(callback)
    }

    override fun onTerminate() {
        super.onTerminate()

        val service = module.provideTimerService()
        service.dispose()
    }
}