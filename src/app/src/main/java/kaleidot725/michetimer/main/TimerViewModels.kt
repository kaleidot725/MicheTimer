package kaleidot725.michetimer.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import kaleidot725.michetimer.models.timer.Timer
import kaleidot725.michetimer.models.timer.TimerRepository
import kaleidot725.michetimer.models.timer.TimerRunnerService

class TimerViewModels(navigator : MicheTimerNavigator, service : TimerRunnerService, repository : TimerRepository) : ViewModel() {
    val all : ObservableList<TimerViewModel> = ObservableArrayList<TimerViewModel>()
    var onAddEvent : ((Int, TimerViewModel) -> Unit) ?= null
    var onRemoveEvent : ((Int, TimerViewModel) -> Unit) ?= null

    private val navigator : MicheTimerNavigator = navigator
    private val service : TimerRunnerService? = service
    private val repository : TimerRepository = repository

    init {
        repository.forEachIndexed { i, t -> all.add(TimerViewModel(navigator, service, repository, i)) }
        repository.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Timer>>(){
            override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    val viewModel = TimerViewModel(navigator, service, repository, positionStart)
                    all.add(viewModel)
                    onAddEvent?.invoke(positionStart, viewModel)
                }
            }

            override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    val viewModel = all.get(positionStart)
                    all.remove(viewModel)
                    onRemoveEvent?.invoke(positionStart, viewModel)
                }
            }

            override fun onChanged(sender: ObservableList<Timer>?) { }
            override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
            override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) { }
        })
    }


}