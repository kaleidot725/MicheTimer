package kaleidot725.michetimer.model.domain.timer

import io.reactivex.subjects.BehaviorSubject
import java.util.*

enum class TimerRunnerState {
    Init, Run, Pause, Timeout
}

interface TimerRunner {
    val id : Int
    val name : String
    val seconds : Long
    val remainSeconds : BehaviorSubject<Long>
    val state : BehaviorSubject<TimerRunnerState>
    val start : Date
    val end : Date

    fun run()
    fun pause()
    fun reset()
    fun dispose()
}