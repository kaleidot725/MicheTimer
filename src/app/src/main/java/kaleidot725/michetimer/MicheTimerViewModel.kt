package kaleidot725.michetimer


import android.arch.lifecycle.ViewModel
import android.view.View

class MicheTimerViewModel(timers : List<kaleidot725.michetimer.Models.Timer>) : ViewModel() {
    var navigator: MicheTimerNavigator? = null
        set(value) {
            timerList?.forEach { i -> i.navigator = value }
            field = value
        }

    val timerList: List<TimerViewModel>

    init {
        timerList = mutableListOf<TimerViewModel>()
        timers.forEach { i ->
            this.timerList.add(TimerViewModel(i))
        }
    }

    fun addTimer(view : View){
        navigator?.addTimer()
    }
}
