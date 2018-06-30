package kaleidot725.michetimer


import android.arch.lifecycle.ViewModel

class MicheTimerViewModel(models : List<kaleidot725.michetimer.Models.Timer>) : ViewModel() {
    var navigator: MicheTimerNavigator? = null
        set(value) {
            timerList?.forEach { i -> i.navigator = value }
        }

    val timerList: List<TimerViewModel>

    private val timers: List<kaleidot725.michetimer.Models.Timer>

    init {
        this.timers = models
        this.timerList = mutableListOf<TimerViewModel>()
        this.timers.forEach { i ->
            this.timerList.add(TimerViewModel(i))
        }
    }
}
