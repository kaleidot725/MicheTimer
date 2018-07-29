package kaleidot725.michetimer.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import kaleidot725.michetimer.models.Timer
import kaleidot725.michetimer.models.ViewModelFactory
import android.content.Intent
import android.databinding.ObservableArrayList
import android.media.SoundPool
import android.support.v7.widget.PopupMenu
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addtimer.AddTimerActivity
import android.view.View
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast

class MicheTimerActivity : AppCompatActivity(), MicheTimerNavigator {

    private lateinit var mediaPlayer : MediaPlayer
    private lateinit var soundPool : SoundPool
    private var soundId : Int = 0
    private lateinit var attributes : AudioAttributes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelFactory.micheTimerNavigator = this
        ViewModelFactory.timers = ObservableArrayList()
        ViewModelFactory.timers?.add(Timer("New", 1))

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = MicheTimerFragment() as Fragment
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        soundPool = SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build()
        soundId = soundPool.load(applicationContext, R.raw.chime, 0)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.chime)
    }

    override fun onStartAlarmTimer(timer: Timer) {
        val mainHandler = Handler(mainLooper)
        var runnable = Runnable {
            mediaPlayer.start()
        }
        mainHandler.post(runnable)
    }

    override fun onStopAlarmTimer(timer: Timer) {
        val mainHandler = Handler(mainLooper)
        var runnable = Runnable {
            mediaPlayer.stop()
        }
        mainHandler.post(runnable)
    }

    override fun onStartEditTimer() {
        val intent = Intent(this, AddTimerActivity::class.java)
        startActivity(intent)
    }

    override fun onStartDeleteTimer(view : View,  menuListener : PopupMenu.OnMenuItemClickListener?) {
        val popup = android.support.v7.widget.PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.timer_menu, popup.menu)
        popup.setOnMenuItemClickListener(menuListener)
        popup.show()
    }
}
