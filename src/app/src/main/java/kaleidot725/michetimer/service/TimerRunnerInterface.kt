package kaleidot725.michetimer.service

import io.reactivex.subjects.BehaviorSubject

enum class TimerRunnerState {
    Init, Run, Pause, Timeout
}

interface TimerRunnerInterface {
    val id : Int
    val name : String
    val seconds : Long
    val remainSeconds : BehaviorSubject<Long>
    val state : BehaviorSubject<TimerRunnerState>

    fun run()
    fun pause()
    fun reset()
    fun finalize()
}