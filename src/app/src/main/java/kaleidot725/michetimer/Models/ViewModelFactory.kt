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
    var timerRepository : TimerRepository? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
        {
            if (micheTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (timerRepository == null)
                throw IllegalStateException("Timers is null")

            val timerViewModels = createTimerViewModels(micheTimerNavigator as MicheTimerNavigator, timerRepository as TimerRepository)
            return MicheTimerViewModel(micheTimerNavigator as MicheTimerNavigator, timerViewModels) as T
        }

        if (modelClass == AddTimerViewModel::class.java) {
            if (addTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (timerRepository == null)
                throw IllegalStateException("Timers is null")

            return AddTimerViewModel(addTimerNavigator as AddTimerNavigator, timerRepository as TimerRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }

    private fun createTimerViewModels(navigator : MicheTimerNavigator, timerRepository: TimerRepository) : ObservableList<TimerViewModel>{
        val timerViewModels = ObservableArrayList<TimerViewModel>()
        timerRepository.findAll().forEach {
            i -> timerViewModels.add(TimerViewModel(navigator, i, timerRepository as TimerRepository))
        }

        return timerViewModels
    }
}
