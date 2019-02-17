package kaleidot725.michetimer.addtimer

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import kaleidot725.michetimer.domain.*
import java.lang.Exception

class EditTimerViewModel(navigator: AddTimerNavigator, repository : TimerRepository, timer : Timer) : BaseTimerViewModel() {
    override val name : MutableLiveData<String> =  MutableLiveData()
    override val error : MutableLiveData<String> = MutableLiveData()
    override var sound : String = AlarmType.convertAlarmTypeToString(timer.alarm)
    override var minute : Long = timer.seconds / 60
    override var second : Long = timer.seconds % 60

    private val id = timer.id
    private val navigator : AddTimerNavigator = navigator
    private val timerRepository : TimerRepository = repository
    private val tag = "EditTimerViewModel"

    init {
        name.value = timer.name
        error.value = getError()
    }

    // View -> ViewModel
    override fun onItemSelectedMinute(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String?
        if (!value.isNullOrEmpty())
            minute = (value as String).toLong()

        error.postValue(getError())
    }

    override fun onItemSelectedSecond(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            second = (value as String).toLong()

        error.postValue(getError())
    }

    override fun onItemSelectedAlarm(parent: AdapterView<*>, view: View, position: Int, id:Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            sound = value as String

        error.postValue(getError())
    }

    override fun onComplete(view : View) {

        try {
            if (hasError()){
                return
            }

            val name    = name.value as String
            val seconds = minute * 60 + second
            val alarm   = AlarmType.convertStringToAlarmType(sound)
            val timer   = Timer(id, name, seconds, alarm)
            timerRepository.update(timer)
            navigator.onComplete()
        }
        catch (e : Exception) {
            Log.v(tag, e.toString())
        }
    }

    private fun getError() : String {
        if (minute == 0L && second == 0L)
            return "Please select minutes and seconds"

        return ""
    }

    private fun hasError() : Boolean{
        if (minute == 0L && second == 0L)
            return true

        return false
    }
}