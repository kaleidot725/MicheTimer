package kaleidot725.michetimer.repository

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class TimerRepositoryJson(context : Context, filename : String) : TimerRepositoryInterface {
    private val context : Context = context
    private val filename : String = filename
    private var list : ObservableList<Timer>
    private var callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>? = null

    init {
        list = loadFromFile(this.filename)
    }

    override fun findAll() : List<Timer>
    {
        return this.list
    }

    override fun findById(id: Int): Timer? {
        return this.list.find { id == it.hashCode() }
    }

    override fun add(item: Timer) {
        this.list.add(item)
        this.callback?.onItemRangeInserted(this.list, this.list.indexOf(item), this.list.count())
        saveToFile(this.filename, this.list)
    }

    override fun remove(item : Timer) {
        val index = this.list.indexOf(item)
        this.list.remove(item)
        this.callback?.onItemRangeRemoved(this.list, index, this.list.count())
        saveToFile(this.filename, this.list);
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

    private fun saveToFile(name : String, list : ObservableList<Timer>) {
        try {
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(List::class.java, Timer::class.java)
            val adapter : JsonAdapter<List<Timer>> = moshi.adapter(type)
            val multitimerJson = adapter.toJson(this.list)
            val output = context.openFileOutput(filename, Context.MODE_PRIVATE)
            output.write(multitimerJson.toByteArray())
        }
        catch (e : Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
        }
    }

    private fun loadFromFile(name : String) : ObservableList<Timer> {
        val observableList = ObservableArrayList<Timer>()
        try {
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(List::class.java, Timer::class.java)
            val adapter : JsonAdapter<List<Timer>> = moshi.adapter(type)
            val input = context.openFileInput(name)
            val json = input.readBytes().toString(Charsets.UTF_8)
            val loadList : List<Timer>? = adapter.fromJson(json)
            loadList?.forEach { observableList.add(it) }
        }
        catch (e : Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
        }

        return observableList
    }
}