package kaleidot725.michetimer.model.service

interface TimerCallback {
    fun notify(indicator : TimerIndicator)
}