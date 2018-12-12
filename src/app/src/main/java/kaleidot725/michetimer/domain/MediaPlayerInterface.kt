package kaleidot725.michetimer.domain

import android.content.Context

interface MediaPlayerInterface {
    val context : Context
    val name : String
    val id : Int
    fun play()
    fun stop()
    fun finalize()
}