package kaleidot725.michetimer.main

import android.app.Application
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
import kaleidot725.michetimer.MainActivityComponent
import kaleidot725.michetimer.MainActivityModule
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerMode
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.disptimer.DispTimerActivity
import kaleidot725.michetimer.domain.Timer
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity(), MainNavigator {

    lateinit var component : MainActivityComponent

    @Inject lateinit var timerRepository : TimerRepository

    @Inject lateinit var timerRunnerService : TimerRunnerService

    @field:[Inject Named("DispTimer")] lateinit var dispTimer : Timer

    @field:[Inject Named("EditTimer")] lateinit var editTimer : Timer

    @Inject lateinit var addTimerMode : AddTimerMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = (application as MicheTimerApplication).component.plus(MainActivityModule(this))
        component.inject(this)

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
        val intent = Intent(applicationContext, AddTimerActivity::class.java)
        addTimerMode.value = AddTimerMode.add
        startActivity(intent)
    }

    override fun onStartEditTimer(timer: Timer) {
        editTimer.apply {
            id = timer.id
            name = timer.name
            seconds = timer.seconds
            sound = timer.sound
        }
        addTimerMode.value = AddTimerMode.edit
        val intent = Intent(applicationContext, AddTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onStartDispTimer(timer: Timer) {
        dispTimer.apply {
            id = timer.id
            name = timer.name
            seconds = timer.seconds
            sound = timer.sound
        }
        val intent = Intent(applicationContext, DispTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onShowLicense() {
        val intent = Intent(applicationContext, OssLicensesMenuActivity::class.java)
        startActivity(intent)
    }

    override fun onShowOption(view : View, listner : PopupMenu.OnMenuItemClickListener) {
        val popup = androidx.appcompat.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.timer_menu, popup.menu)
        popup.setOnMenuItemClickListener(listner)
        popup.show()
    }
}
