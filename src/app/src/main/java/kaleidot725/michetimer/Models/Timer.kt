package kaleidot725.michetimer.Models

import android.arch.lifecycle.MutableLiveData
import java.util.*
import java.util.Timer
import kotlin.concurrent.timerTask

class Timer(name : String, seconds : Long) {
    val name : String = name
    val seconds : Long = seconds

    var remainSeconds : MutableLiveData<Long> = MutableLiveData()
    var state : MutableLiveData<TimerState> = MutableLiveData()

    private var beginSeconds : Long = seconds
    private var beginDate : Date = Date()
    private var timer : Timer? = null

    init {
        remainSeconds.postValue(seconds)
        state.postValue(TimerState.Init)
    }

    fun run() {
        if (state == TimerState.Run || state == TimerState.Timeup)
            throw IllegalStateException()

        beginDate = Date()
        beginSeconds = remainSeconds.value ?: 0

        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask { countdown() }, 0, 100)
        state.postValue(TimerState.Run)
    }

    fun pause() {
        if (state == TimerState.Init || state == TimerState.Pause || state == TimerState.Timeup)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds.postValue(beginSeconds - diffSeconds(beginDate, Date()))
        state.postValue(TimerState.Pause)
    }

    fun reset() {
        if (state == TimerState.Init)
            throw IllegalStateException()

        timer?.cancel()
        remainSeconds.postValue(seconds)
        state.postValue(TimerState.Init)
    }

    private fun countdown() {
        remainSeconds.postValue(beginSeconds - diffSeconds(beginDate, Date()))
        if ((remainSeconds.value ?: 0) <= 0) {
            timer?.cancel()
            remainSeconds.postValue( 0)
            state.postValue(TimerState.Timeup)
        }
    }

    private fun diffSeconds(begin : Date, end : Date) : Long {
        return (end.time - begin.time) / 1000
    }
}