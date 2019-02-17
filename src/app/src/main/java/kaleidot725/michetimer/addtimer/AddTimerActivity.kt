package kaleidot725.michetimer.addtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.*
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerService
import javax.inject.Inject
import javax.inject.Named

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    lateinit var component : AddTimerActivityComponent

    @Inject lateinit var timerRepository : TimerRepository

    @Inject lateinit var timerService : TimerService

    @field:[Inject Named("EditTimer")] lateinit var editTimer : Timer

    @Inject lateinit var addTimerMode : AddTimerMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        val appComponent = (application as MicheTimerApplication).component
        component = appComponent.plus(AddTimerActivityModule(this))
        component.inject(this)

        title = addTimerMode.valueString
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddTimerFragment()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onComplete() {
        this.finish()
    }
}
