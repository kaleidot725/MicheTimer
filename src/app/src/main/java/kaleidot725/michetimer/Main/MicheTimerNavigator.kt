package kaleidot725.michetimer.main

import android.support.v7.widget.PopupMenu
import android.view.View

interface MicheTimerNavigator {
    fun onStartAlarmTimer(name : String)
    fun onStartEditTimer()
    fun onStartDeleteTimer(view : View,  menuListener : PopupMenu.OnMenuItemClickListener?)
}