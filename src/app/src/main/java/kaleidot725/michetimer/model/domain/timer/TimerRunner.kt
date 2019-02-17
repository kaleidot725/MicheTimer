package kaleidot725.michetimer.model.domain.timer

import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class TimerRunner(id : Int, name : String, seconds : Long) : TimerRunnerInterface, TimerRunnerController {
    override val id : Int = id
    override val name : String = name
    override val seconds : Long = seconds
    override val remainSeconds : BehaviorSubject<Long> =  BehaviorSubject.create()
    override val state : BehaviorSubject<TimerRunnerState> =  BehaviorSubject.create()
    override var start : Date = Date()
    override var end : Date = Date()

    private var countdownSeconds : Long = this.seconds
    private var tickTimer : Timer? = null

    init {
        this.state.onNext(TimerRunnerState.Init)
        this.remainSeconds.onNext(this.seconds)
    }

    override fun run() {
        if (state == TimerRunnerState.Run || state == TimerRunnerState.Timeout)
            throw IllegalStateException()

        tickTimer = Timer()
        tickTimer?.scheduleAtFixedRate(timerTask { countdown() }, 0, 100)
        start = Date()
        state.onNext(TimerRunnerState.Run)
    }

    override fun pause() {
        if (state == TimerRunnerState.Init || state == TimerRunnerState.Pause || state == TimerRunnerState.Timeout)
            throw IllegalStateException()

        tickTimer?.cancel()
        countdownSeconds -= diffSeconds(start, Date())
        state.onNext(TimerRunnerState.Pause)
    }

    override fun reset() {
        if (state == TimerRunnerState.Init)
            throw IllegalStateException()

        tickTimer?.cancel()
        countdownSeconds = this.seconds
        remainSeconds.onNext(this.seconds)
        state.onNext(TimerRunnerState.Init)
    }

    override fun finalize()
    {
        reset()
    }

    private fun countdown() {
        val diff = countdownSeconds - diffSeconds(start, Date())

        // タイムアウトしたか確認する
        if (diff < 0) {
            tickTimer?.cancel()
            remainSeconds.onNext(0)
            end = Date()
            state.onNext(TimerRunnerState.Timeout)
            return
        }

        remainSeconds.onNext(diff)
    }

    private fun diffSeconds(begin : Date, end : Date) = (end.time - begin.time) / 1000
}