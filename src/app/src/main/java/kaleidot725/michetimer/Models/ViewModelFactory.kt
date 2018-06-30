package kaleidot725.michetimer.Models

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kaleidot725.michetimer.MicheTimerViewModel

class ViewModelFactory() : ViewModelProvider.Factory{

    private val timers : List<Timer> =
            listOf( Timer("One", 10),
                    Timer("Two", 120),
                    Timer("Three", 180),
                    Timer("Four", 240))

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
            return MicheTimerViewModel(timers) as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}
