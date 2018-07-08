package kaleidot725.michetimer.Models

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kaleidot725.michetimer.Main.MicheTimerNavigator
import kaleidot725.michetimer.MicheTimerViewModel

object ViewModelFactory : ViewModelProvider.Factory{
    var navigator : MicheTimerNavigator? = null
    var timers : List<Timer>? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
            return MicheTimerViewModel(navigator as MicheTimerNavigator, timers as List<Timer>) as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}
