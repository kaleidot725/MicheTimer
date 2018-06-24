package kaleidot725.michetimer


import android.arch.lifecycle.ViewModel

class MicheTimerViewModel(models : List<kaleidot725.michetimer.Models.Timer>) : ViewModel() {
    private val timers : List<kaleidot725.michetimer.Models.Timer>
    val timerList : List<TimerViewModel>

    init {
        this.timers = models
        this.timerList = mutableListOf<TimerViewModel>()
        this.timers.forEach { i -> this.timerList.add(TimerViewModel(i)) }
    }
}