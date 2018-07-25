package kaleidot725.michetimer.models

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.addtimer.AddTimerViewModel
import kaleidot725.michetimer.main.MicheTimerNavigator
import kaleidot725.michetimer.main.MicheTimerViewModel
import kaleidot725.michetimer.main.TimerViewModel

object ViewModelFactory : ViewModelProvider.Factory{
    var micheTimerNavigator : MicheTimerNavigator? = null
    var addTimerNavigator : AddTimerNavigator? = null
    var timers : ObservableList<Timer>? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
        {
            if (micheTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (timers == null)
                throw IllegalStateException("Timers is null")

            val timerViewModels = createTimerViewModels(micheTimerNavigator as MicheTimerNavigator, timers as ObservableList<Timer>)
            return MicheTimerViewModel(micheTimerNavigator as MicheTimerNavigator, timerViewModels) as T
        }

        if (modelClass == AddTimerViewModel::class.java) {
            if (addTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (timers == null)
                throw IllegalStateException("Timers is null")

            return AddTimerViewModel(addTimerNavigator as AddTimerNavigator, timers as ObservableArrayList<Timer>) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }

    private fun createTimerViewModels(navigator : MicheTimerNavigator, timers : ObservableList<Timer>) : ObservableList<TimerViewModel>{
        val timerViewModels = ObservableArrayList<TimerViewModel>()
        timers.forEach {
            i -> timerViewModels.add(TimerViewModel(navigator, i, timers))
        }

        return timerViewModels
    }
}
