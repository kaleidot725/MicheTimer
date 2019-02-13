package kaleidot725.michetimer.domain

import android.content.Context

interface AlarmPlayer {
    val context : Context
    val name : String
    val id : Int
    val isPlaying : Boolean
    val isRepeating : Boolean

    fun play()
    fun stop()
    fun finalize()
}