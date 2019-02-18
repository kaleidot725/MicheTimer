package kaleidot725.michetimer.model.service

import io.reactivex.subjects.BehaviorSubject
import kaleidot725.michetimer.model.domain.timer.TimerRunnerState
import java.util.*

interface TimerIndicator {
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
}