package kaleidot725.michetimer.model.repository

interface Persistence<T> {
    fun save(list: List<T>)
    fun load() : List<T>
}