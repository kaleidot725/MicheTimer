package kaleidot725.michetimer.models

import android.content.Context
import android.databinding.ObservableList

interface Repository<T> : Iterable<T> {
    fun findAll() : List<T>
    fun findById(id : Int) : T?
    fun add(item : T)
    fun remove(item : T)
    fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>)
}