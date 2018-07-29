package kaleidot725.michetimer.main

import android.support.v7.widget.PopupMenu
import android.view.View
import kaleidot725.michetimer.models.Timer

interface MicheTimerNavigator {
    fun onStartAlarmTimer(timer : Timer)
    fun onStopAlarmTimer(timer : Timer)
    fun onStartEditTimer()
    fun onStartDeleteTimer(view : View,  menuListener : PopupMenu.OnMenuItemClickListener?)
}