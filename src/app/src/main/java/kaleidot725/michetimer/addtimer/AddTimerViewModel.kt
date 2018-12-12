package kaleidot725.michetimer.addtimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kaleidot725.michetimer.repository.Timer
import kaleidot725.michetimer.repository.TimerRepository
import java.lang.Exception

class AddTimerViewModel(navigator: AddTimerNavigator, timerRepository  : TimerRepository) : ViewModel() {
    val name : MutableLiveData<String> =  MutableLiveData()
    val sound : MutableLiveData<String> = MutableLiveData()
    var minute : MutableLiveData<Long> = MutableLiveData()
    var second : MutableLiveData<Long> = MutableLiveData()

    private val navigator : AddTimerNavigator = navigator
    private val timerRepository : TimerRepository = timerRepository
    private val tag = "AddTimerViewModel"

    init {
        name.value = "New Timer"
        sound.value = "chime"
        minute.value = 0
        second.value = 0
    }

    fun onItemSelectedMinute(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String
        minute.postValue(value.toLong())
    }

    fun onItemSelectedSecond(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String
        second.postValue(value.toLong())
    }

    fun onItemSelectedAlarm(parent: AdapterView<*>, view: View, position: Int, id:Long) {
        val value = parent.selectedItem as String
        sound.postValue(value)
    }

    fun onComplete(view : View) {
        if (name.value.isNullOrEmpty())
            return

        if (minute.value == null && second.value == null)
            return

        if (minute.value == 0L && second.value == 0L)
            return

        try {
            val blankId = timerRepository.getBlankId()
            val name    = name.value   as String
            val seconds = minute.value as Long * 60 + second.value as Long
            val sound   = sound.value  as String
            val timer   = Timer(blankId, name, seconds, sound)
            timerRepository.add(timer)
            navigator.onComplete()
        }
        catch (e : Exception) {
            Log.v(tag, e.toString())
        }
    }

    fun TimerRepository.getBlankId() : Int {
        val sorted = timerRepository.findAll().sortedBy { it.id }
        var blankId = sorted.count()
        sorted.forEachIndexed { i, t -> if (t.id != i)  { blankId = i  } }
        return blankId
    }
}