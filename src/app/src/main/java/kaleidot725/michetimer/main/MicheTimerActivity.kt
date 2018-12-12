package kaleidot725.michetimer.main

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import kaleidot725.michetimer.R
import android.view.View
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.repository.TimerRepository
import kaleidot725.michetimer.service.TimerRunnerService
import android.content.Intent
import kaleidot725.michetimer.micheTimerNavigator
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(R.id.license_button == item?.itemId) {
            onShowLicense()
            return true
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        micheTimerNavigator = this
        timerRepository = TimerRepository(this.applicationContext.filesDir.path + "setting.json")

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

    override fun onShowLicense() {
        val intent = Intent(this, OssLicensesMenuActivity::class.java)
        startActivity(intent)
    }

    override fun onShowOption(view : View, listner : PopupMenu.OnMenuItemClickListener) {
        val popup = android.support.v7.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.timer_menu, popup.menu)
        popup.setOnMenuItemClickListener(listner)
        popup.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this.connection)
    }
}
