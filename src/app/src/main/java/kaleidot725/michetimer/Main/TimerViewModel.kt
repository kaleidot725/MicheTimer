package kaleidot725.michetimer.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.view.View
import kaleidot725.michetimer.R
import kaleidot725.michetimer.models.*

class TimerViewModel(navigator : MicheTimerNavigator, service : TimerService, repository: TimerRepository, index : Int) : ViewModel() {
    val name : String
    val seconds : String
    val state : MutableLiveData<String>
    val remainSeconds : MutableLiveData<String>

    private val tag : String = "TimerViewModel"
    private val navigator : MicheTimerNavigator
    private val service : TimerService?
    private val repository : TimerRepository
    private val index : Int
    private var runner : TimerRunner?
    private val timer : Timer
    private val listener : PopupMenu.OnMenuItemClickListener

    init
    {
        this.navigator = navigator
        this.service = service
        this.repository = repository
        this.index = index
        this.runner = null
        this.timer = repository.elementAt(index)

        this.name = timer.name
        this.seconds = toRemainSecondsString(timer.seconds)
        this.state = MutableLiveData()
        this.state.postValue(toStateString(TimerState.Init))
        this.remainSeconds = MutableLiveData()
        this.remainSeconds.postValue(this.seconds)
        this.listener = PopupMenu.OnMenuItemClickListener {
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
    }

    fun run(view: View) {
        if (this.runner == null) {
            this.runner = service?.register(this.timer) as TimerRunner
            this.runner?.state?.subscribe {
                try {
                    this.state.postValue(toStateString(it))
                }
                catch(e : java.lang.Exception) {
                    Log.v("TimerViewModel", e.toString())
                }
            }
            this.runner?.remainSeconds?.subscribe {
                try {
                    this.remainSeconds.postValue(toRemainSecondsString(it))
                }
                catch(e : java.lang.Exception) {
                    Log.v("TimerViewModel", e.toString())
                }
            }
        }

        val nonNullRunner = this.runner as TimerRunner
        try {
            when (nonNullRunner.state.value) {
                TimerState.Init -> {
                    nonNullRunner.run()
                }
                TimerState.Run -> {
                    nonNullRunner.pause()
                }
                TimerState.Pause -> {
                    nonNullRunner.run()
                }
                TimerState.Timeout -> {
                    nonNullRunner.reset()
                    service?.unregister(nonNullRunner)
                    this.runner = null
                }
            }
        }
        catch (e : Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun reset(view: View) {
        if (this.runner == null) {
            return
        }

        val nonNullRunner = this.runner as TimerRunner
        try {
            nonNullRunner.reset()
            service?.unregister(nonNullRunner)
        } catch (e: Exception) {
            Log.d(tag, e.toString())
        }
    }

    fun popupOption(view : View) {
        navigator.onShowOption(view, this.listener)
    }

    private fun toRemainSecondsString(remainSeconds : Long) =
        "${(remainSeconds / 60).toString().padStart(2,'0')}: " +
        "${(remainSeconds % 60).toString().padStart(2,'0')}"

    private fun toStateString(state : TimerState) =
            when (state) {
                TimerState.Run     -> { "Pause"   }
                TimerState.Timeout -> { "Stop"    }
                TimerState.Init    -> { "Start"   }
                TimerState.Pause   -> { "Start"   }
            }
}