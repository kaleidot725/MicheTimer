package kaleidot725.michetimer.main

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.service.TimerRunnerService

class TimerViewModels(navigator : MicheTimerNavigator, service : TimerRunnerService, repository : TimerRepository) : ViewModel() {
    val all : ObservableList<TimerViewModel> = ObservableArrayList<TimerViewModel>()
    var onAddEvent : ((Int) -> Unit) ?= null
    var onRemoveEvent : ((Int) -> Unit) ?= null
    var onChanged : (() -> Unit) ?= null

    private val navigator : MicheTimerNavigator = navigator
    private val service : TimerRunnerService? = service
    private val repository : TimerRepository = repository

    init {
        repository.findAll().forEach { t -> all.add(TimerViewModel(navigator, t, service, repository)) }
        repository.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Timer>>(){
            override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    val viewModel = TimerViewModel(navigator, sender[positionStart], service, repository)
                    all.add(positionStart, viewModel)
                    onAddEvent?.invoke(positionStart)
                }
            }

            override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    val viewModel = all[positionStart]
                    all.remove(viewModel)
                    onRemoveEvent?.invoke(positionStart)
                }
            }

            override fun onChanged(sender: ObservableList<Timer>?) {
                if (sender != null) {
                    sender?.forEach {
                        val viewModel = TimerViewModel(navigator, it, service, repository)
                        all.clear()
                        all.add(viewModel)
                    }

                    onChanged?.invoke()
                }
            }

            override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
            override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) { }
        })
    }
}