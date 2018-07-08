package kaleidot725.michetimer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import kaleidot725.michetimer.Main.MicheTimerNavigator
import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.Models.TimerState

class TimerViewModel(navigator : MicheTimerNavigator, timer : Timer) : ViewModel() {
    val navigator : MicheTimerNavigator = navigator
    val name : String = timer.name
    val state : MutableLiveData<String> = MutableLiveData()
    val remainSeconds : MutableLiveData<String> = MutableLiveData()

    private val tag : String = "TimerViewModel"
    private val timer : Timer = timer

    init {
        timer.state.subscribe {
            this.state.postValue(toStateString(it))
        }

        timer.remainSeconds.subscribe {
            this.remainSeconds.postValue(toRemainSecondsString(it))
            if (it == 0L)
                this.navigator.onStartAlarmTimer(this.name)
        }
    }

    fun run(view: View) {
        try {
            when (this.timer.state.value) {
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
                }
            }
        }
        catch (e : Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun reset(view: View) {
        try {
            timer.reset()
        }
        catch (e : Exception) {
            Log.d(tag, e.toString())
        }
    }

    private fun toRemainSecondsString(remainSeconds : Long) =
            "${(remainSeconds / 60).toString().padStart(2,'0')}:" +
            "${(remainSeconds % 60).toString().padStart(2,'0')}"

    private fun toStateString(state : TimerState) =
            when (state) {
                TimerState.Run     -> { "Pause"   }
                TimerState.Timeout -> { "Pause"   }
                else               -> { "Start"   }
            }
}