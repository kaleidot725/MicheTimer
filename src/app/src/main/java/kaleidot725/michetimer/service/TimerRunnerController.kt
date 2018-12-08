package kaleidot725.michetimer.service

import io.reactivex.subjects.BehaviorSubject

interface TimerRunnerController {
    val name : String
    val seconds : Long
    val remainSeconds : BehaviorSubject<Long>
    val state : BehaviorSubject<TimerRunnerState>

    fun run()
    fun pause()
    fun reset()
}