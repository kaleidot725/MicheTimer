package kaleidot725.michetimer.main


import androidx.lifecycle.ViewModel
import android.view.View

class MicheTimerViewModel(navigator : MicheTimerNavigator) : ViewModel() {
    val navigator: MicheTimerNavigator = navigator

    fun onStartAddTimer(view : View){
        navigator.onStartAddTimer()
    }
}
