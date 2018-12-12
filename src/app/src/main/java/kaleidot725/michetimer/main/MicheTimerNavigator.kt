package kaleidot725.michetimer.main

import androidx.appcompat.widget.PopupMenu
import android.view.View

interface MicheTimerNavigator {
    fun onStartEditTimer()
    fun onShowLicense()
    fun onShowOption(view : View, menuListener : PopupMenu.OnMenuItemClickListener)
}