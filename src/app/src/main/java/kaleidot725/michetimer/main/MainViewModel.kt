package kaleidot725.michetimer.main

import android.opengl.Visibility
import android.view.View
import androidx.databinding.ObservableList
import androidx.lifecycle.*
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.model.service.TimerService


class MainViewModel(navigator : MainNavigator, service : TimerService, repository : TimerRepository, filterType : MainFilter, search : String) : ViewModel() {

    private val _all : MutableLiveData<List<MainTimerViewModel>> = MutableLiveData()
    val all : LiveData<List<MainTimerViewModel>> = _all

    private val _visibility : MediatorLiveData<Int> = MediatorLiveData<Int>().also { v ->
        v.addSource(all) { all -> v.value = if(all.count() == 0)  View.VISIBLE else View.GONE }
    }
    val visibility : LiveData<Int> = _visibility

    var onAddEvent : ((Int) -> Unit) ?= null
    var onRemoveEvent : ((Int) -> Unit) ?= null
    var onChanged : (() -> Unit) ?= null

    private val navigator : MainNavigator = navigator
    private val service : TimerService = service
    private val repository : TimerRepository = repository
    private val filter : MainFilter = filterType
    private val search : String = search

    private val changedCallback = object : ObservableList.OnListChangedCallback<ObservableList<Timer>>() {
        override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            if (sender != null) {
                _all.value = repository.filter(filterType).map { MainTimerViewModel(navigator, it, service, repository) }
                onAddEvent?.invoke(positionStart)
            }
        }

        override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            if (sender != null) {
                _all.value = repository.filter(filterType).map { MainTimerViewModel(navigator, it, service, repository) }
                _visibility.value = if(repository.filter(filterType).count() == 0)  View.VISIBLE else View.GONE
                onRemoveEvent?.invoke(positionStart)
            }
        }

        override fun onChanged(sender: ObservableList<Timer>?) {
            if (sender != null) {
                _all.value = repository.filter(filterType).map { MainTimerViewModel(navigator, it, service, repository) }
                _visibility.value = if(repository.filter(filterType).count() == 0)  View.VISIBLE else View.GONE
                onChanged?.invoke()
            }
        }

        override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
        override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) { }
    }

    init {
        _all.value = repository.filter(filterType).map { MainTimerViewModel(navigator, it, service, repository) }
        _visibility.value = if(repository.filter(filterType).count() == 0)  View.VISIBLE else View.GONE
        repository.addOnListChangedCallback(changedCallback)
    }

    fun onStartAddTimer(view : View){
        navigator.onStartAddTimer()
    }

    fun TimerRepository.filter(filter : MainFilter) : List<Timer>{
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