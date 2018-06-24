package kaleidot725.michetimer.addtimer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableList
import android.view.View
import android.widget.NumberPicker
import kaleidot725.michetimer.models.Timer

class AddTimerViewModel(navigator: AddTimerNavigator, timers : ObservableList<Timer>) : ViewModel() {
    val name : MutableLiveData<String> =  MutableLiveData()
    var hour : MutableLiveData<Long> = MutableLiveData()
    var minute : MutableLiveData<Long> = MutableLiveData()
    var second : MutableLiveData<Long> = MutableLiveData()

    private val navigator : AddTimerNavigator = navigator
    private val timers = timers

    init {
        name.value = "New Timer"
        hour.value = 0
        minute.value = 0
        second.value = 0
    }

    fun onComplete(view : View) {
        if (!name.value.isNullOrEmpty() && hour.value != null && minute.value != null && second.value != null)
        {
            val timer = Timer(name.value as String, (hour.value as Long * 60 * 60) + (minute.value as Long * 60) + second.value as Long)
            timers.add(timer)
            navigator.onComplete()
        }
    }
}