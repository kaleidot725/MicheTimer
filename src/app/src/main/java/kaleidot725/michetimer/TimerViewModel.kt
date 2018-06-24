package kaleidot725.michetimer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.Models.TimerState
import java.util.*

class TimerViewModel(timer : Timer) : ViewModel() {
    val name : MutableLiveData<String>
    val state : MutableLiveData<String>
    val remainSeconds : MutableLiveData<String>


    private val timer : Timer = timer
    private var updater : java.util.Timer? = java.util.Timer()

    init {
        name = MutableLiveData()
        name.postValue(timer.name)

        state = MutableLiveData()
        state.postValue(toStateString(timer.state))

        remainSeconds = MutableLiveData()
        remainSeconds.postValue(toRemainSecondsString(timer.remainSeconds))


    }

    fun run(view: View) {
        try {
            when (timer.state) {
                TimerState.Init -> {
                    timer.run()
                }
                TimerState.Run -> {
                    timer.pause()
                }
                TimerState.Pause -> {
                    timer.run()
                }
                else -> {
                    timer.reset()
                }
            }

            state.postValue(toStateString(timer.state))
        }
        catch (e : Exception) {

        }
    }

    fun reset(view: View) {
        try {
            timer.reset()
            state.postValue(toStateString(timer.state))
            remainSeconds.postValue(toRemainSecondsString(timer.remainSeconds))
        }
        catch (e : Exception) {

        }
    }

    private fun toRemainSecondsString(remainSeconds : Long) =
            "${(remainSeconds / 60).toString().padStart(2,'0')}:" +
            "${(remainSeconds % 60).toString().padStart(2,'0')}"

    private fun toStateString(state : TimerState) =
            when (state) {
                TimerState.Run -> { "Stop"  }
                else           -> { "Start" }
            }
}