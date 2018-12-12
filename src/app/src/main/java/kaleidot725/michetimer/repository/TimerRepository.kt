package kaleidot725.michetimer.repository

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.ParameterizedType

class TimerRepository(filePath : String) : Repository<Timer> {

    private val filePath : String = filePath
    private var callback : ObservableList.OnListChangedCallback<ObservableList<Timer>>? = null
    private val moshi    : Moshi = Moshi.Builder().build()
    private val type     : ParameterizedType = Types.newParameterizedType(List::class.java, Timer::class.java)
    private val adapter  : JsonAdapter<List<Timer>> = moshi.adapter(type)
    private var list     : ObservableList<Timer> = this.load()

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
        this.save(this.list)
    }

    override fun remove(item : Timer) {
        val index = this.list.indexOf(item)
        this.list.remove(item)
        this.callback?.onItemRangeRemoved(this.list, index, this.list.count())
        this.save(this.list)
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

    fun save(list: ObservableList<Timer>){
        try {
            val output = FileOutputStream(File(filePath))
            output.use {
                val json = adapter.toJson(list)
                output.write(json.toByteArray())
            }
        }
        catch (e : Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
        }
    }

    fun load() : ObservableList<Timer> {
        val observableList = ObservableArrayList<Timer>()

        try {
            val input = FileInputStream(File(filePath))
            input.use {
                val json = input.readBytes().toString(Charsets.UTF_8)
                val list : List<Timer>? = adapter.fromJson(json)
                list?.forEach { observableList.add(it) }
            }
        }
        catch (e : Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
        }

        return observableList
    }
}