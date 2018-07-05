package kaleidot725.michetimer

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.Models.ViewModelFactory

interface MicheTimerNavigator {
    fun onStartAlarmTimer(name : String)
    fun onStartEditTimer()
}

class MicheTimerActivity : AppCompatActivity(), MicheTimerNavigator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelFactory.navigator = this
        ViewModelFactory.timers =
                listOf( Timer("One", 10),
                Timer("Two", 120),
                Timer("Three", 180),
                Timer("Four", 240))

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = MicheTimerFragment() as Fragment
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onStartAlarmTimer(name: String) {
        val mainHandler = Handler(mainLooper)
        var runnable = Runnable {
            Toast.makeText(this, "$name Timeout!!!", Toast.LENGTH_SHORT).show()
        }
        mainHandler.post(runnable)
    }

    override fun onStartEditTimer() {
        val mainHandler = Handler(mainLooper)
        var runnable = Runnable {
            Toast.makeText(this, "Add!!!", Toast.LENGTH_SHORT).show()
        }
        mainHandler.post(runnable)
    }
}
