package kaleidot725.michetimer.model.repository

import androidx.databinding.ObservableList
import kaleidot725.michetimer.model.entity.Timer

interface Repository<T>  {
    fun findAll() : List<T>
    fun findById(id : Int) : T?

    fun add(item : T)
    fun remove(item : T)
    fun update(item : T)

    fun count() : Int
    fun next() : Int

    fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<T>>)
    fun removeOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>)
}