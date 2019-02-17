package kaleidot725.michetimer.main

import androidx.appcompat.widget.PopupMenu
import android.view.View
import kaleidot725.michetimer.model.entity.Timer

interface MainNavigator {
    fun onStartAddTimer()
    fun onStartEditTimer(timer : Timer)
    fun onStartDispTimer(timer : Timer)
    fun onShowSetting()
    fun onShowLicense()
    fun onShowOption(view : View, menuListener : PopupMenu.OnMenuItemClickListener)
}