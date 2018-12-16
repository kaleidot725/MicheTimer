package kaleidot725.michetimer.domain

import androidx.databinding.ObservableList

interface Repository<T> : Iterable<T> {
    fun findAll() : List<T>
    fun findById(id : Int) : T?
    fun add(item : T)
    fun remove(item : T)
    fun count() : Int
    fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<T>>)
}