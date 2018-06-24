package kaleidot725.michetimer.Models

import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscriber
import java.util.*
import java.util.Timer

class Timer(name : String, seconds : Long) {
    var name : String = name
    var seconds : Long = seconds
    var remainSeconds : Long = seconds
    var state : TimerState = TimerState.Init

    private var beginSeconds : Long = seconds
    private var beginDate : Date = Date()
    private var timer : Timer? = null

    fun run() {
        if (state == TimerState.Run || state == TimerState.Timeup)
            throw IllegalStateException()

        beginDate = Date()
        beginSeconds = remainSeconds
        state = TimerState.Run
    }

    fun pause() {
        if (state == TimerState.Init || state == TimerState.Pause || state == TimerState.Timeup)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds = beginSeconds - diffSeconds(beginDate, Date())
        state = TimerState.Pause
    }

    fun update() : Long {
        if (state != TimerState.Run)
            throw IllegalStateException()

        remainSeconds = beginSeconds - diffSeconds(beginDate, Date())
        if (remainSeconds <= 0) {
            timer?.cancel()
            remainSeconds = 0
            state = TimerState.Timeup
        }

        return remainSeconds
    }

    fun reset() {
        if (state == TimerState.Init)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds = seconds
        state = TimerState.Init
    }

    private fun diffSeconds(begin : Date, end : Date) : Long {
        return (end.time - begin.time) / 1000
    }
}