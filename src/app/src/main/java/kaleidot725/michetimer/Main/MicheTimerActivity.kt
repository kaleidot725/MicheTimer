package kaleidot725.michetimer.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.widget.Toast
import kaleidot725.michetimer.models.Timer
import kaleidot725.michetimer.models.ViewModelFactory
import android.content.Intent
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerActivity


class MicheTimerActivity : AppCompatActivity(), MicheTimerNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelFactory.micheTimerNavigator = this
        ViewModelFactory.timers = ObservableArrayList()
        ViewModelFactory.timers?.add(Timer("New", 100))

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
        val intent = Intent(this, AddTimerActivity::class.java)
        startActivity(intent)
    }
}
