package kaleidot725.michetimer.addtimer

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseTimerViewModel : ViewModel(){
    abstract val name : MutableLiveData<String>
    abstract val error : MutableLiveData<String>
    abstract var sound : String
    abstract var minute : Long
    abstract var second : Long

    abstract fun onItemSelectedMinute(parent: AdapterView<*>, view: View, position: Int, id: Long)
    abstract fun onItemSelectedSecond(parent: AdapterView<*>, view: View, position: Int, id: Long)
    abstract fun onItemSelectedAlarm(parent: AdapterView<*>, view: View, position: Int, id:Long)
    abstract fun onComplete(view : View)
}