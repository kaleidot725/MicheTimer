package kaleidot725.michetimer.models

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kaleidot725.michetimer.R
import java.util.*

class TimerService() : Service() {
    private val tag : String = "TimerService"
    private val binder : IBinder = ServiceBinder()
    private val runners : MutableList<Pair<TimerRunner, SoundPlayer>> = mutableListOf()

    private class SoundPlayer(context : Context) {
        private val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        private val soundPool = SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build()
        private val soundId = soundPool.load(context, R.raw.chime, 0)
        private val mediaPlayer = MediaPlayer.create(context, R.raw.chime)

        fun play() {
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }

        fun stop() {
            mediaPlayer.stop()
        }

        fun isPlaying() : Boolean = mediaPlayer.isPlaying()

        fun finalize()
        {
            soundPool.unload(soundId)
            mediaPlayer.release()
        }
    }


    override fun onCreate() {
        Log.v(tag, "onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.v(tag, "onBind")
        return this.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(tag, "onUnbind")
        return super.onUnbind(intent)
    }

    fun register(timer : Timer) : TimerRunner {
        val player = SoundPlayer(applicationContext)
        val runner = TimerRunner(timer.name, timer.seconds)
        runner.remainSeconds.subscribe {
            if (it == 0L)
                player.play()
        }

        runners.add(runner to player)
        return runner
    }

    fun unregister(runner : TimerRunner) {
        val pair = runners.filter { p -> p.first.equals(runner) }.first()
        runners.remove(pair)

        val runner = pair.first
        runner.reset()

        val player = pair.second
        player.finalize()
    }

    inner class ServiceBinder : Binder() {
        val service: TimerService get() = this@TimerService
    }
}
