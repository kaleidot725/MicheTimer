package kaleidot725.michetimer.addtimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kaleidot725.michetimer.R
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.timerRepository

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    companion object {
        val addMode : Int = 0
        val editMode : Int = 1

        fun create(context : Context, mode : Int, id : Int) : Intent {
            val intent = Intent(context, AddTimerActivity::class.java).apply {
                putExtra("mode", mode)
                putExtra("id", id)
            }

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        val mode = intent.getIntExtra("mode", AddTimerActivity.addMode)
        when(mode)
        {
            addMode -> {
                setTitle("Add Timer")
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = AddTimerFragment().apply { vmFactory = AddTimerViewModelFactory() }
                transaction.replace(R.id.container, fragment)
                transaction.commit()
            }
            editMode -> {
                setTitle("Edit Timer")
                val transaction = supportFragmentManager.beginTransaction()
                val id : Int = intent.getIntExtra("id", -1)
                val fragment = AddTimerFragment().apply { vmFactory = EditTimerViewModelFactory(timerRepository?.findById(id) as Timer) }
                transaction.replace(R.id.container, fragment)
                transaction.commit()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onComplete() {
        this.finish()
    }

    private inner class EditTimerViewModelFactory(timer : Timer) : ViewModelProvider.Factory {
        private val timer : Timer = timer

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EditTimerViewModel(this@AddTimerActivity, timerRepository, timer) as T
        }
    }

    private inner class AddTimerViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddTimerViewModel(this@AddTimerActivity, timerRepository) as T
        }
    }
}
