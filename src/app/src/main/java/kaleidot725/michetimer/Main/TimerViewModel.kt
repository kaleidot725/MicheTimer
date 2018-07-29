package kaleidot725.michetimer.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableList
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.view.View
import kaleidot725.michetimer.R
import kaleidot725.michetimer.models.Timer
import kaleidot725.michetimer.models.TimerState

class TimerViewModel(navigator : MicheTimerNavigator, timer : Timer, timers : ObservableList<Timer>) : ViewModel() {
    val navigator : MicheTimerNavigator = navigator
    val name : String = timer.name
    val state : MutableLiveData<String> = MutableLiveData()
    val remainSeconds : MutableLiveData<String> = MutableLiveData()

    private val tag : String = "TimerViewModel"
    private val timer : Timer = timer
    private val timers : ObservableList<Timer> = timers

    init {
        timer.state.subscribe {
            this.state.postValue(toStateString(it))
        }

        timer.remainSeconds.subscribe {
            this.remainSeconds.postValue(toRemainSecondsString(it))
            if (it == 0L)
                this.navigator.onStartAlarmTimer(timer)
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
                TimerState.Timeout -> {
                    this.navigator.onStopAlarmTimer(timer)
                    timer.reset()
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
        } catch (e: Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun popupOption(view : View){
        try {
            navigator.onStartDeleteTimer(view, PopupMenu.OnMenuItemClickListener {
                when(it?.itemId) {
                    R.id.delete -> {
                        this.reset(view)
                        timers.remove(timer)
                        true
                    }
                    else -> {
                        false
                    }
                }
            })
        } catch (e : Exception) {
            Log.d(tag, e.toString())
        }
    }

    private fun toRemainSecondsString(remainSeconds : Long) =
            "${(remainSeconds / 60).toString().padStart(2,'0')}:" +
            "${(remainSeconds % 60).toString().padStart(2,'0')}"

    private fun toStateString(state : TimerState) =
            when (state) {
                TimerState.Run     -> { "Pause"   }
                TimerState.Timeout -> { "Stop"    }
                TimerState.Init    -> { "Start"   }
                TimerState.Pause   -> { "Start"   }
                else               -> { "Unknown" }
            }
}