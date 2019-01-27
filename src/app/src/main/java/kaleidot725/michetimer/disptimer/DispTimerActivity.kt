package kaleidot725.michetimer.disptimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kaleidot725.michetimer.R
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService
import java.lang.Exception


class DispTimerActivity : AppCompatActivity() , DispTimerNavigator {

    companion object {
        fun create(context : Context, id : Int) : Intent {
            val intent = Intent(context, DispTimerActivity::class.java).apply {
                putExtra("id", id)
            }

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disp_timer)

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
            return DispTimerViewModel(this@DispTimerActivity, timer, timerService, timerRepository) as T
        }
    }
}
