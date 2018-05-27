package kaleidot725.michetimer.Models

import java.util.*

class UserTimer(name : String, seconds : Int) {
    val name : String = name
    val seconds : Int = seconds

    private val timer : Timer = Timer()
    private val remainSeconds : Int = 0
    private val state : UserTimerState = UserTimerState.Stop

    fun Start() {
    }

    fun Stop() {

    }

    fun Pause() {

    }
}