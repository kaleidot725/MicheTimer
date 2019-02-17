package kaleidot725.michetimer.model.domain.alarm

import android.content.Context

interface AlarmPlayer {
    val context : Context
    val type : Int
    val isPlaying : Boolean
    val isRepeating : Boolean

    fun play()
    fun stop()
    fun dispose()
}