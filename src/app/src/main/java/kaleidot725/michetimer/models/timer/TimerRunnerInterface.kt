package kaleidot725.michetimer.models.timer

import io.reactivex.subjects.BehaviorSubject
import kaleidot725.michetimer.models.timer.TimerRunner.State

interface TimerRunnerInterface {
    val id : Int
    val name : String
    val seconds : Long
    val remainSeconds : BehaviorSubject<Long>
    val state : BehaviorSubject<State>

    fun run()
    fun pause()
    fun reset()
    fun finalize()
}