package kaleidot725.michetimer.main

import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.widget.PopupMenu
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerActivity
import android.view.View
import android.os.IBinder
import android.util.Log
import kaleidot725.michetimer.models.*
import kaleidot725.michetimer.models.timer.TimerRepository
import kaleidot725.michetimer.models.timer.TimerRunnerService

class MicheTimerActivity : AppCompatActivity(), MicheTimerNavigator {
    val connection : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // FIXME シングルトンでの変数保持をやめる
            timerService = (service as TimerRunnerService.ServiceBinder).instance
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = MicheTimerFragment() as Fragment
            transaction.replace(R.id.container, fragment)
            transaction.commit()
            Log.v("tag", "onServiceConnected")
        }

        override fun onServiceDisconnected(name : ComponentName?) {
            // FIXME シングルトンでの変数保持をやめる
            timerService = null
            Log.v("tag", "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FIXME シングルトンでの変数保持をやめる
        micheTimerNavigator = this
        timerRepository = TimerRepository(this.applicationContext, "setting.json")

        val intent = Intent(this, TimerRunnerService::class.java)
        startService(intent)
        bindService(intent, this.connection, Context.BIND_ADJUST_WITH_ACTIVITY)
        Log.v("tag", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.v("tag", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("tag", "onResume")
    }

    override fun onStartEditTimer() {
        val intent = Intent(this, AddTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onShowOption(view : View, listner : PopupMenu.OnMenuItemClickListener) {
        val popup = android.support.v7.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.timer_menu, popup.menu)
        popup.setOnMenuItemClickListener(listner)
        popup.show()
    }
}
