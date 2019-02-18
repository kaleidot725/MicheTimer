package kaleidot725.michetimer.model.repository

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.ParameterizedType


class PersistenceFile<T>(name : String, clazz : Class<T>) : Persistence<T>
{
    val name : String = name

    private val moshi : Moshi = Moshi.Builder().build()
    private val type : ParameterizedType = Types.newParameterizedType(List::class.java, clazz)
    private val adapter : JsonAdapter<List<T>> = moshi.adapter(type)

    override fun save(list: List<T>)
    {
        try {
            val output = FileOutputStream(File(name))
            output.use {
                val json = adapter.toJson(list)
                output.write(json.toByteArray())
            }
        }
        catch (e : Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
            throw e
        }
    }

    override fun load() : List<T> {
        val list = mutableListOf<T>()

        try {
            val input = FileInputStream(File(name))
            input.use {
                input.readBytes().toString(Charsets.UTF_8).apply {
                    adapter.fromJson(this)?.apply {
                        forEach {
                            list.add(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(this.javaClass.name.toString(), e.toString())
        }

        return list
    }
}