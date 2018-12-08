package kaleidot725.michetimer.service

import android.content.Context

interface TimerMediaPlayerInterface {
    val context : Context
    val name : String
    val id : Int
    fun play()
    fun stop()
    fun finalize()
}