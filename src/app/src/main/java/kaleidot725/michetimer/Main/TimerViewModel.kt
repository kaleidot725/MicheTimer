package kaleidot725.michetimer.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableList
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.view.View
import kaleidot725.michetimer.R
import kaleidot725.michetimer.models.*

class TimerViewModel(navigator : MicheTimerNavigator, service : TimerService, repository: TimerRepository, index : Int) : ViewModel() {
    val name : String
    val state : MutableLiveData<String> = MutableLiveData()
    val remainSeconds : MutableLiveData<String> = MutableLiveData()

    private val tag : String = "TimerViewModel"
    private val navigator : MicheTimerNavigator = navigator
    private val service : TimerService? = service
    private var runner : TimerRunner = service.register(repository.elementAt(index))
    private val timer : Timer = repository.elementAt(index)

    private val listener : PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener {
        when(it?.itemId) {
            R.id.delete -> {
                repository.remove(timer)
                true
            }
            else -> {
                false
            }
        }
    }

    init {
        this.name = timer.name
        this.state.postValue(toStateString(TimerState.Init))
        this.remainSeconds.postValue(toRemainSecondsString(timer.seconds))

        this.runner.state.subscribe {
            this.state.postValue(toStateString(it))
        }

        this.runner.remainSeconds.subscribe {
            this.remainSeconds.postValue(toRemainSecondsString(it))
        }
    }

    fun run(view: View) {
        try {
            when (this.runner.state.value) {
                TimerState.Init -> {
                    val r = timerService?.register(timer)
                    if (r != null) {
                        this.runner = r
                        runner.run()
                    }
                }
                TimerState.Run -> {
                    runner.pause()
                }
                TimerState.Pause -> {
                    runner.run()
                }
                TimerState.Timeout -> {
                    runner.reset()
                }
            }
        }
        catch (e : Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun reset(view: View) {
        try {
            runner.reset()
        } catch (e: Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun popupOption(view : View){
        navigator.onShowOption(view, this.listener)
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