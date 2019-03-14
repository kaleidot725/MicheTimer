package kaleidot725.michetimer.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import android.view.View
import android.util.Log
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerService
import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kaleidot725.michetimer.MainActivityComponent
import kaleidot725.michetimer.MainActivityModule
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerMode
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.disptimer.DispTimerActivity
import kaleidot725.michetimer.model.entity.Timer
import javax.inject.Inject
import javax.inject.Named
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import kaleidot725.michetimer.setting.SettingActivity
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity(), MainNavigator {

    lateinit var component : MainActivityComponent

    @Inject lateinit var timerRepository : TimerRepository

    @Inject lateinit var timerService : TimerService

    @field:[Inject Named("DispTimer")] lateinit var dispTimer : Timer

    @field:[Inject Named("EditTimer")] lateinit var editTimer : Timer

    @Inject lateinit var addTimerMode : AddTimerMode

    lateinit var fragment : MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = (application as MicheTimerApplication).component.plus(MainActivityModule(this))
        component.inject(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Home"
        toolbar.inflateMenu(R.menu.toolbar_menu)

        val filter = findViewById<ActionMenuItemView>(R.id.filter)
        filter?.setOnClickListener {
            val popup = androidx.appcompat.widget.PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.menu_filter, popup.menu)

            val listener = PopupMenu.OnMenuItemClickListener {
                when(it?.itemId) {
                    R.id.NameAsc -> {
                        val transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        this.fragment = MainFragment.create(MainFilter.NameAsc, "")
                        transaction.replace(R.id.container, this.fragment)
                        transaction.commit()
                        true
                    }
                    R.id.NameDesc -> {
                        val transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        this.fragment = MainFragment.create(MainFilter.NameDesc, "")
                        transaction.replace(R.id.container, this.fragment)
                        transaction.commit()
                        true
                    }
                    R.id.SecondsAsc -> {
                        val transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        this.fragment = MainFragment.create(MainFilter.SecondsAsc, "")
                        transaction.replace(R.id.container, this.fragment)
                        transaction.commit()
                        true
                    }
                    R.id.SecondsDesc -> {
                        val transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        this.fragment = MainFragment.create(MainFilter.SecondsDesc, "")
                        transaction.replace(R.id.container, this.fragment)
                        transaction.commit()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

            popup.setOnMenuItemClickListener(listener)
            popup.show()
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.navigation_license -> onShowLicense()
            }

            menuItem.isChecked = false
            drawerLayout.closeDrawers()
            true
        }

        val transaction = supportFragmentManager.beginTransaction()
        this.fragment = MainFragment.create(MainFilter.None, "")
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
        editTimer.also {
            it.id = timer.id
            it.name = timer.name
            it.seconds = timer.seconds
            it.alarm = timer.alarm
        }
        addTimerMode.value = AddTimerMode.edit
        val intent = Intent(applicationContext, AddTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onStartDispTimer(timer: Timer) {
        dispTimer.also {
            it.id = timer.id
            it.name = timer.name
            it.seconds = timer.seconds
            it.alarm = timer.alarm
        }
        val intent = Intent(applicationContext, DispTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onShowSetting() {
        val intent = Intent(applicationContext, SettingActivity::class.java)
        startActivity(intent)
    }

    override fun onShowLicense() {
        LibsBuilder()
                .withActivityTitle("License")
                .withShowLoadingProgress(false)
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR).start(this)
    }

    override fun onShowOption(view : View, listner : PopupMenu.OnMenuItemClickListener) {
        val popup = androidx.appcompat.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_timer, popup.menu)
        popup.setOnMenuItemClickListener(listner)
        popup.show()
    }
}
