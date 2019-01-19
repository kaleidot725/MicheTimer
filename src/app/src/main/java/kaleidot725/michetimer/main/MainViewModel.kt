package kaleidot725.michetimer.main

import android.view.View
import androidx.lifecycle.ViewModel


class MainViewModel(navigator : MainNavigator) : ViewModel() {
    val navigator: MainNavigator = navigator

    fun onStartAddTimer(view : View){
        navigator.onStartAddTimer()
    }
}