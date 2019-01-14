package kaleidot725.michetimer.main

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.PopupMenu
import android.view.View
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.service.TimerRunnerService
import android.content.Intent
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kaleidot725.michetimer.R
import kaleidot725.michetimer.domain.FilePersistence
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.micheTimerNavigator
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService
import kotlinx.android.synthetic.main.activity_main.*

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

        micheTimerNavigator = this

        val f = this.applicationContext.filesDir.path + "setting.json"
        val p = FilePersistence(f, Timer::class.java)
        timerRepository = TimerRepository(p)

        val intent = Intent(this, TimerRunnerService::class.java)
        startService(intent)
        bindService(intent, this.connection, Context.BIND_ADJUST_WITH_ACTIVITY)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ).apply {
            syncState()
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.navigation_license) {
                onShowLicense()
            }
            else {
                menuItem.isChecked = true
            }

            drawerLayout.closeDrawers()
            true
        }

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

    override fun onStartAddTimer() {
        val intent = AddTimerActivity.create(this, AddTimerActivity.addMode, -1)
        startActivity(intent)
    }

    override fun onStartEditTimer(timer: Timer) {
        val intent = AddTimerActivity.create(this, AddTimerActivity.editMode, timer.id)
        startActivity(intent)
    }

    override fun onShowLicense() {
        val intent = Intent(this, OssLicensesMenuActivity::class.java)
        startActivity(intent)
    }

    override fun onShowOption(view : View, listner : PopupMenu.OnMenuItemClickListener) {
        val popup = androidx.appcompat.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.timer_menu, popup.menu)
        popup.setOnMenuItemClickListener(listner)
        popup.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this.connection)
    }
}
