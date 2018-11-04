package kaleidot725.michetimer.models.timer

import android.databinding.ObservableList

interface TimerRepositoryInterface : Iterable<Timer> {
    fun findAll() : List<Timer>
    fun findById(id : Int) : Timer?
    fun add(item : Timer)
    fun remove(item : Timer)
    fun count() : Int
    fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>)
}