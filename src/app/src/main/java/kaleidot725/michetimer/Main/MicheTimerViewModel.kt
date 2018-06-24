package kaleidot725.michetimer.main


import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.view.View
import kaleidot725.michetimer.models.Timer

class MicheTimerViewModel(navigator : MicheTimerNavigator, timerViewModels : ObservableList<TimerViewModel>) : ViewModel() {
    val navigator: MicheTimerNavigator = navigator
    val timerViewModels: ObservableList<TimerViewModel> = timerViewModels

    fun onStartEditTimer(view : View){
        navigator.onStartEditTimer()
    }
}
