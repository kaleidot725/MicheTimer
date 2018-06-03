package kaleidot725.michetimer.Models

import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class Timer(name : String, seconds : Long) {
    val name : String = name
    val seconds : Long = seconds
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

        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask { countdown() }, 0, 100)
        state = TimerState.Run
    }

    fun pause() {
        if (state == TimerState.Init || state == TimerState.Pause || state == TimerState.Timeup)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds = beginSeconds - diffSeconds(beginDate, Date())
        state = TimerState.Pause
    }

    fun reset() {
        if (state == TimerState.Init)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds = seconds
        state = TimerState.Init
    }

    private fun countdown() {
        remainSeconds = beginSeconds - diffSeconds(beginDate, Date())
        if (remainSeconds <= 0) {
            timer?.cancel()
            remainSeconds = 0
            state = TimerState.Timeup
        }
    }

    private fun diffSeconds(begin : Date, end : Date) : Long {
        return (end.time - begin.time) / 1000
    }
}