package kaleidot725.michetimer.domain

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.rxkotlin.toObservable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.reflect.ParameterizedType

class TimerRepository(persistence: Persistence<Timer>) : Repository<Timer> {

    private val persistence : Persistence<Timer> = persistence
    private val list     : ObservableList<Timer> = ObservableArrayList()
    private var callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>? = null

    init {
        try {
            list.addAll(persistence.load())
        }
        catch(e : Exception)
        {
            print("unexpected error msg: ${e.message}")
        }
    }

    override fun findAll() : List<Timer>
    {
        return this.list
    }

    override fun findById(id: Int): Timer? {
        return this.list.find { id == it.id }
    }

    override fun add(item: Timer) {
        if (findById(item.id) != null) {
            throw IllegalArgumentException("duplicated id")
        }

        this.list.add(item)
        this.callback?.onItemRangeInserted(this.list, this.list.indexOf(item), this.list.count())
        this.callback?.onChanged(this.list)
        this.callback?.onItemRangeChanged(this.list, this.list.indexOf(item), this.list.count())
        this.persistence.save(this.list)
    }

    override fun remove(item : Timer) {
        if (findById(item.id) == null) {
            throw IllegalArgumentException("not found id")
        }

        val index = this.list.indexOf(item)
        this.list.remove(item)
        this.callback?.onItemRangeRemoved(this.list, index, this.list.count())
        this.callback?.onChanged(this.list)
        this.callback?.onItemRangeChanged(this.list, this.list.indexOf(item), this.list.count())
        this.persistence.save(this.list)
    }

    override fun count() : Int {
        return this.list.count()
    }

    override fun iterator(): Iterator<Timer> {
        return list.iterator()
    }

    override fun addOnListChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>) {
        this.callback = callback
    }
}