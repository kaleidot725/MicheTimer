package kaleidot725.michetimer.service

import kaleidot725.michetimer.domain.TimerRunnerController

interface TimerRunnerServiceInterface {
    fun register(id : Int, name : String, seconds : Long, sound : String): TimerRunnerController
    fun resolve(id : Int) : TimerRunnerController
    fun unregister(id : Int)
}