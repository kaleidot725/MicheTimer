package kaleidot725.michetimer.disptimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.*
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.domain.timer.TimerRunnerService
import javax.inject.Inject
import javax.inject.Named

class DispTimerActivity : AppCompatActivity() , DispTimerNavigator {

    lateinit var component : DispTimerActivityComponent

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerRunnerService : TimerRunnerService

    @field:[Inject Named("DispTimer")] lateinit var dispTimer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disp_timer)

        val appComponent = (application as MicheTimerApplication).component
        component = appComponent.plus(DispTimerActivityModule(this))
        component.inject(this)

        title = dispTimer.name

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = DispTimerFragment()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
