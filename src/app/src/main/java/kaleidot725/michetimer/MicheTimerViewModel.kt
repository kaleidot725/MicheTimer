package kaleidot725.michetimer


import android.arch.lifecycle.ViewModel
import android.view.View

class MicheTimerViewModel(navigator : MicheTimerNavigator,  timers : List<kaleidot725.michetimer.Models.Timer>) : ViewModel() {
    val navigator: MicheTimerNavigator
    val timers: List<TimerViewModel>

    init {
        this.navigator = navigator
        this.timers = mutableListOf<TimerViewModel>()
        timers.forEach {
            i -> this.timers.add(TimerViewModel(navigator, i))
        }
    }

    fun onStartEditTimer(view : View){
        navigator.onStartEditTimer()
    }
}
