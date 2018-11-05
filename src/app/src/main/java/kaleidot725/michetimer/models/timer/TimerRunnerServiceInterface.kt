package kaleidot725.michetimer.models.timer

interface TimerRunnerServiceInterface {
    fun register(timer: Timer): TimerRunnerInterface
    fun unregister(timer: Timer)
}