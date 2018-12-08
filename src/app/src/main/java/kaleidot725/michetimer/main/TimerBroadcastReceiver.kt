package kaleidot725.michetimer.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "TimerBroadcastReceiver"

class TimerBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val TIMER_RESET = "kaleidot725.michetimer.main.intent.action.TIMER_RESET"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.v("Intent", intent?.action)
    }
}