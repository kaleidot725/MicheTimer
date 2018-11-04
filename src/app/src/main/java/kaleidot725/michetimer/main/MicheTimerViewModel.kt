package kaleidot725.michetimer.main


import android.arch.lifecycle.ViewModel
import android.view.View

class MicheTimerViewModel(navigator : MicheTimerNavigator) : ViewModel() {
    val navigator: MicheTimerNavigator = navigator

    fun onStartEditTimer(view : View){
        navigator.onStartEditTimer()
    }
}
