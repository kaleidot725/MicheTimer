package kaleidot725.michetimer.main

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.domain.timer.TimerRunnerService


class MainViewModel(navigator : MainNavigator, service : TimerRunnerService, repository : TimerRepository, filter : MainFilter, search : String) : ViewModel() {

    val all : ObservableList<MainTimerViewModel> = ObservableArrayList<MainTimerViewModel>()
    var onAddEvent : ((Int) -> Unit) ?= null
    var onRemoveEvent : ((Int) -> Unit) ?= null
    var onChanged : (() -> Unit) ?= null

    private val navigator : MainNavigator = navigator
    private val service : TimerRunnerService = service
    private val repository : TimerRepository = repository
    private val filter : MainFilter = filter
    private val search : String = search

    private val changedCallback = object : ObservableList.OnListChangedCallback<ObservableList<Timer>>() {
        override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            if (sender != null) {
                all.clear()
                repository.filter(filter, search).forEach { t -> all.add(MainTimerViewModel(navigator, t, service, repository)) }
                onAddEvent?.invoke(positionStart)
            }
        }

        override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            if (sender != null) {
                all.clear()
                repository.filter(filter, search).forEach { t -> all.add(MainTimerViewModel(navigator, t, service, repository)) }
                onRemoveEvent?.invoke(positionStart)
            }
        }

        override fun onChanged(sender: ObservableList<Timer>?) {
            if (sender != null) {
                all.clear()
                repository.filter(filter, search).forEach { t -> all.add(MainTimerViewModel(navigator, t, service, repository)) }
                onChanged?.invoke()
            }
        }

        override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
        override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) { }
    }

    init {
        repository.filter(filter, search).forEach { t -> all.add(MainTimerViewModel(navigator, t, service, repository)) }
        repository.addOnListChangedCallback(changedCallback)
    }

    fun onStartAddTimer(view : View){
        navigator.onStartAddTimer()
    }

    fun TimerRepository.filter(filter : MainFilter, search : String) : List<Timer>{
        when(filter){
            MainFilter.None -> {
                return repository.findAll()
            }
            MainFilter.NameAsc -> {
                return repository.findAll().sortedBy { it.name }
            }
            MainFilter.NameDesc -> {
                return  repository.findAll().sortedByDescending { it.name }
            }
            MainFilter.SecondsAsc -> {
                return repository.findAll().sortedBy { it.seconds }
            }
            MainFilter.SecondsDesc -> {
                return repository.findAll().sortedByDescending { it.seconds }
            }
            else -> return repository.findAll()
        }
    }
}