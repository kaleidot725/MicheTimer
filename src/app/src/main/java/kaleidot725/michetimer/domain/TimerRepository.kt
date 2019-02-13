package kaleidot725.michetimer.domain

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList

class TimerRepository(persistence: Persistence<Timer>) : Repository<Timer>{

    private val persistence : Persistence<Timer> = persistence
    private var list     : ObservableList<Timer> = ObservableArrayList()

    init {
        list.addAll(persistence.load())
    }

    override fun add(item: Timer) {
        if (findById(item.id) != null) {
            throw IllegalArgumentException("duplicated id")
        }

        this.list.add(item)
        this.persistence.save(this.list)
    }

    override fun remove(item : Timer) {
        if (findById(item.id) == null) {
            throw IllegalArgumentException("not found id")
        }

        val index = this.list.indexOf(item)
        this.list.remove(item)
        this.persistence.save(this.list)
    }

    override fun update(item: Timer) {
        val rm = findById(item.id)
        if (rm == null) {
            throw java.lang.IllegalArgumentException("not found id")
        }

        val index = this.list.indexOf(rm)
        this.list.remove(rm)
        this.list.add(index, item)
        this.persistence.save(this.list)
    }

    override fun next() : Int {
        val sorted = this.findAll().sortedBy { it.id }
        var next = sorted.count()
        sorted.forEachIndexed { i, t -> if (t.id != i)  { next = i  } }
        return next
    }

    override fun findById(id: Int): Timer? {
        return this.list.find { id == it.id }
    }

    override fun findAll() : List<Timer>
    {
        return this.list
    }

    override fun count() : Int {
        return this.list.count()
    }

    override fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>) {
        this.list.addOnListChangedCallback(callback)
    }
}