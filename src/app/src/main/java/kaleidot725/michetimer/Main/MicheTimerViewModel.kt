package kaleidot725.michetimer.main


import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.view.View
import kaleidot725.michetimer.models.Timer

class MicheTimerViewModel(navigator : MicheTimerNavigator) : ViewModel() {
    val navigator: MicheTimerNavigator = navigator

    fun onStartEditTimer(view : View){
        navigator.onStartEditTimer()
    }
}
