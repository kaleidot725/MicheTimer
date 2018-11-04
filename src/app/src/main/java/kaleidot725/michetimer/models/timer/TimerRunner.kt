package kaleidot725.michetimer.models.timer

import android.content.Context
import android.media.MediaPlayer
import io.reactivex.subjects.BehaviorSubject
import kaleidot725.michetimer.R
import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class TimerRunner(context : Context, name : String, seconds : Long, sound : String) : TimerRunnerInterface {
    enum class State {
        Init, Run, Pause, Timeout
    }

    override val name : String = name
    override val seconds : Long = seconds
    override val remainSeconds : BehaviorSubject<Long>
    override val state : BehaviorSubject<State>

    private var countdownSeconds : Long = this.seconds
    private var beginData : Date = Date()
    private var tickTimer : Timer? = null
    private val mediaPlayer = when(sound) {
        "chime" -> {
            MediaPlayer.create(context, R.raw.chime)
        }
        "timeup" -> {
            MediaPlayer.create(context, R.raw.timeup)
        }
        else -> {
            MediaPlayer.create(context, R.raw.chime)
        }
    }

    init {
        this.state = BehaviorSubject.create()
        this.state.onNext(State.Init)
        this.remainSeconds = BehaviorSubject.create()
        this.remainSeconds.onNext(this.seconds)
    }

    override fun run() {
        if (state == State.Run || state == State.Timeout)
            throw IllegalStateException()

        tickTimer = Timer()
        tickTimer?.scheduleAtFixedRate(timerTask { countdown() }, 0, 100)
        beginData = Date()
        state.onNext(State.Run)
    }

    override fun pause() {
        if (state == State.Init || state == State.Pause || state == State.Timeout)
            throw IllegalStateException()

        tickTimer?.cancel()
        countdownSeconds -= diffSeconds(beginData, Date())
        state.onNext(State.Pause)
    }

    override fun reset() {
        if (state == State.Init)
            throw IllegalStateException()

        tickTimer?.cancel()
        countdownSeconds = this.seconds
        remainSeconds.onNext(this.seconds)
        state.onNext(State.Init)
        mediaPlayer.stop()
    }

    override fun finalize()
    {
        mediaPlayer.release()
    }

    private fun countdown() {
        val diff = countdownSeconds - diffSeconds(beginData, Date())
        if (0 < diff) {
            remainSeconds.onNext(diff)
        }
        else {
            tickTimer?.cancel()
            remainSeconds.onNext(0)
            mediaPlayer.start()
            state.onNext(State.Timeout)
        }
    }

    private fun diffSeconds(begin : Date, end : Date) = (end.time - begin.time) / 1000
}