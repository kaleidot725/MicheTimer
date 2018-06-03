package kaleidot725.michetimer.MicheTimer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kaleidot725.michetimer.Models.Timer

class UserTimerViewModel(timer : Timer) : ViewModel() {
    private val timer : Timer = timer

    val name : MutableLiveData<String> = MutableLiveData()
    val seconds : MutableLiveData<String> = MutableLiveData()

    init {
        name.value = timer.name
        seconds.value = timer.seconds.toString()
    }

    fun Start() {

    }

    fun Stop() {

    }

    fun Pause() {

    }
}