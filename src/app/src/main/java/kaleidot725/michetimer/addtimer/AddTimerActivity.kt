package kaleidot725.michetimer.addtimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.*
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import javax.inject.Inject
import javax.inject.Named

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    lateinit var component : AddTimerActivityComponent

    @Inject lateinit var timerRepository : TimerRepository

    @Inject lateinit var timerRunnerService : TimerRunnerService

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
