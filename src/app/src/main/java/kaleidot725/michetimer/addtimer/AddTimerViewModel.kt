package kaleidot725.michetimer.addtimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import java.lang.Exception

class AddTimerViewModel(navigator: AddTimerNavigator, timerRepository  : TimerRepository) : ViewModel() {
    val name : MutableLiveData<String> =  MutableLiveData()
    val error : MutableLiveData<String> = MutableLiveData()
    var sound : String = "chime"
    var minute : Long = 0
    var second : Long = 0

    private val navigator : AddTimerNavigator = navigator
    private val timerRepository : TimerRepository = timerRepository
    private val tag = "AddTimerViewModel"

    init {
        name.value = "New Timer"
        error.value = getError()
    }

    // View -> ViewModel
    fun onItemSelectedMinute(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String?
        if (!value.isNullOrEmpty())
            minute = (value as String).toLong()

        error.postValue(getError())
    }

    fun onItemSelectedSecond(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            second = (value as String).toLong()

        error.postValue(getError())
    }

    fun onItemSelectedAlarm(parent: AdapterView<*>, view: View, position: Int, id:Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            sound = value as String

        error.postValue(getError())
    }

    private fun getError() : String {
        if (minute == 0L && second == 0L)
            return "Please select minutes and seconds"

        return ""
    }

    fun hasError() : Boolean{
        if (minute == 0L && second == 0L)
            return true

        return false
    }

    fun onComplete(view : View) {
        fun TimerRepository.getBlankId() : Int {
            val sorted = timerRepository.findAll().sortedBy { it.id }
            var blankId = sorted.count()
            sorted.forEachIndexed { i, t -> if (t.id != i)  { blankId = i  } }
            return blankId
        }

        try {
            if (hasError()) return
            val blankId = timerRepository.getBlankId()
            val name    = name.value as String
            val seconds = minute * 60 + second
            val sound   = sound  as String
            val timer   = Timer(blankId, name, seconds, sound)
            timerRepository.add(timer)
            navigator.onComplete()
        }
        catch (e : Exception) {
            Log.v(tag, e.toString())
        }
    }
}