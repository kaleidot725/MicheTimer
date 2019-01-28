package kaleidot725.michetimer.disptimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kaleidot725.michetimer.R
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import javax.inject.Inject


class DispTimerActivity : AppCompatActivity() , DispTimerNavigator {

    companion object {
        fun create(context : Context, id : Int) : Intent {
            val intent = Intent(context, DispTimerActivity::class.java).apply {
                putExtra("id", id)
            }

            return intent
        }
    }

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerRunnerService : TimerRunnerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disp_timer)

        (application as MicheTimerApplication).component.inject(this)

        val id = intent.getIntExtra("id", -1)
        val timer = timerRepository.findById(id)
        if (timer == null) {
            finish()
        }

        title = (timer as Timer).name

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = DispTimerFragment().apply {
            vmFactory = DispTimerViewModelFactory(timer as Timer)
        }
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private inner class DispTimerViewModelFactory(timer : Timer) : ViewModelProvider.Factory {
        private val timer : Timer = timer

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DispTimerViewModel(this@DispTimerActivity, timer, timerRunnerService, timerRepository) as T
        }
    }
}
