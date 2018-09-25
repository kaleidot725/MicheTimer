package kaleidot725.michetimer.models

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.view.Display
import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.addtimer.AddTimerViewModel
import kaleidot725.michetimer.main.MicheTimerNavigator
import kaleidot725.michetimer.main.MicheTimerViewModel
import kaleidot725.michetimer.main.TimerViewModel

object ViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
        {
            if (ModelSingletons.micheTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (ModelSingletons.timerRepository == null)
                throw IllegalStateException("Timers is null")

            val timerViewModels = createTimerViewModels(ModelSingletons.micheTimerNavigator as MicheTimerNavigator, ModelSingletons.timerRepository as TimerRepository)
            return MicheTimerViewModel(ModelSingletons.micheTimerNavigator as MicheTimerNavigator, timerViewModels) as T
        }

        if (modelClass == AddTimerViewModel::class.java) {
            if (ModelSingletons.addTimerNavigator == null)
                throw IllegalStateException("MicheTimerNavigator is null")

            if (ModelSingletons.timerRepository == null)
                throw IllegalStateException("Timers is null")

            return AddTimerViewModel(ModelSingletons.addTimerNavigator as AddTimerNavigator, ModelSingletons.timerRepository as TimerRepository) as T
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
