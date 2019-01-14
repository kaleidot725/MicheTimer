package kaleidot725.michetimer.domain

import androidx.databinding.ObservableList

interface Persistence<T> {
    fun save(list: List<T>)
    fun load() : List<T>
}