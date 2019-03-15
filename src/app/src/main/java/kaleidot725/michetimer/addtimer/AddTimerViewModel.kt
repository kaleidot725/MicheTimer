package kaleidot725.michetimer.addtimer

import androidx.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kaleidot725.michetimer.model.domain.alarm.AlarmType
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import java.lang.Exception

class AddTimerViewModel(navigator: AddTimerNavigator, timerRepository  : TimerRepository) : BaseTimerViewModel() {

    override val name : MutableLiveData<String> =  MutableLiveData()
    override val error : MutableLiveData<String> = MutableLiveData()
    override var sound : String = AlarmType.convertAlarmTypeToString(AlarmType.Bellstar)
    override var minute : Long = 0
    override var second : Long = 0

    private val navigator : AddTimerNavigator = navigator
    private val timerRepository : TimerRepository = timerRepository
    private val tag = "AddTimerViewModel"

    init {
        name.value = "New Timer"
        error.value = getError()
    }

    // View -> ViewModel
    override fun onItemSelectedMinute(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String?
        if (!value.isNullOrEmpty())
            minute = (value).toLong()

        error.postValue(getError())
    }

    override fun onItemSelectedSecond(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            second = (value).toLong()

        error.postValue(getError())
    }

    override fun onItemSelectedAlarm(parent: AdapterView<*>, view: View, position: Int, id:Long) {
        val value = parent.selectedItem as String
        if (!value.isNullOrEmpty())
            sound = value

        error.postValue(getError())
    }

    override fun onComplete(view : View) {

        try {
            if (hasError()){
                return
            }

            val id = timerRepository.next()
            val name    = name.value as String
            val seconds = minute * 60 + second
            val alarm   = AlarmType.convertStringToAlarmType(sound)
            val timer   = Timer(id, name, seconds, alarm)
            timerRepository.add(timer)
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