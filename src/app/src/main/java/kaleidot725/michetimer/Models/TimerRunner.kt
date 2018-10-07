package kaleidot725.michetimer.models

import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class TimerRunner(name : String , seconds : Long) {
    val name : String = name
    val seconds : Long = seconds
    val remainSeconds : BehaviorSubject<Long>
    val state : BehaviorSubject<TimerState>

    private var countdownSeconds : Long = this.seconds
    private var beginDate : Date = Date()
    private var timer : Timer? = null

    init {
        this.state = BehaviorSubject.create()
        this.state.onNext(TimerState.Init)
        this.remainSeconds = BehaviorSubject.create()
        this.remainSeconds.onNext(this.seconds)
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
        countdownSeconds = this.seconds
        remainSeconds.onNext(this.seconds)
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