package kaleidot725.michetimer.main

import android.support.v7.widget.PopupMenu
import android.view.View

interface MicheTimerNavigator {
    fun onStartEditTimer()
    fun onShowOption(view : View, menuListener : PopupMenu.OnMenuItemClickListener)
}