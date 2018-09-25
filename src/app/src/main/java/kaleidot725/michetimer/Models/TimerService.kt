package kaleidot725.michetimer.models

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Binder
import android.os.IBinder
import kaleidot725.michetimer.R

class TimerService() : Service() {
    private val binder : IBinder = ServiceBinder()
    private lateinit var repository : TimerRepository
    private lateinit var mediaPlayer : MediaPlayer
    private lateinit var soundPool : SoundPool
    private var soundId : Int = 0
    private lateinit var attributes : AudioAttributes

    override fun onCreate() {
        attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        soundPool = SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build()
        soundId = soundPool.load(applicationContext, R.raw.chime, 0)
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.chime)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        return this.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    fun alarmStart(timer : Timer) {
        mediaPlayer.start()
    }

    fun alarmStop(timer : Timer) {
        mediaPlayer.stop()
    }

    inner class ServiceBinder : Binder() {
        val service: TimerService get() = this@TimerService
    }
}
