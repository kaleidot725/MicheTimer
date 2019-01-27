package kaleidot725.michetimer.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import android.view.View
import android.util.Log
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import kaleidot725.michetimer.R
import kaleidot725.michetimer.disptimer.DispTimerActivity
import kaleidot725.michetimer.domain.FilePersistence
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService

class MainActivity : AppCompatActivity(), MainNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val f = this.applicationContext.filesDir.path + "setting.json"
        val p = FilePersistence(f, Timer::class.java)
        timerRepository = TimerRepository(p)
        timerService = TimerRunnerService(applicationContext)

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

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = MainFragment()
        fragment.vmFactory = MainViewModelFactory()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

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

    override fun onStartDispTimer(timer: Timer) {
        val intent = DispTimerActivity.create(this, timer.id)
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

    private inner class MainViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == MainViewModel::class.java) {
                return MainViewModel(
                        this@MainActivity ,
                        timerService as TimerRunnerService,
                        timerRepository as TimerRepository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
