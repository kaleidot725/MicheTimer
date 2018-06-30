package kaleidot725.michetimer.Models

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class Timer(name : String, seconds : Long) {
    val name : String
    val seconds : Long
    var remainSeconds : BehaviorSubject<Long>
    var state : BehaviorSubject<TimerState>

    private var countdownSeconds : Long = seconds
    private var beginDate : Date = Date()
    private var timer : Timer? = null

    init {
        this.name = name
        this.seconds = seconds
        this.remainSeconds = BehaviorSubject.create()
        this.remainSeconds.onNext(seconds)
        this.state = BehaviorSubject.create()
        this.state.onNext(TimerState.Init)
    }

    fun run() {
        if (state == TimerState.Run || state == TimerState.Timeout)
            throw IllegalStateException()

        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask { countdown() }, 0, 100)
        beginDate = Date()
        state.onNext(TimerState.Run)
    }

    fun pause() {
        if (state == TimerState.Init || state == TimerState.Pause || state == TimerState.Timeout)
            throw IllegalStateException()

        timer?.cancel()
        countdownSeconds -= diffSeconds(beginDate, Date())
        state.onNext(TimerState.Pause)
    }

    fun reset() {
        if (state == TimerState.Init)
            throw IllegalStateException()

        timer?.cancel()
        countdownSeconds = seconds
        remainSeconds.onNext(seconds)
        state.onNext(TimerState.Init)
    }

    private fun countdown() {
        val diff = countdownSeconds - diffSeconds(beginDate, Date())
        if (0 < diff) {
            remainSeconds.onNext(diff)
        }
        else {
            timer?.cancel()
            remainSeconds.onNext(0)
            state.onNext(TimerState.Timeout)
        }
    }

    private fun diffSeconds(begin : Date, end : Date) = (end.time - begin.time) / 1000
}