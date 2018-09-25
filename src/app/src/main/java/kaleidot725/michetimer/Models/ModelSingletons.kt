package kaleidot725.michetimer.models

import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.main.MicheTimerNavigator

object ModelSingletons {
    var timerService : TimerService? = null
    var micheTimerNavigator : MicheTimerNavigator? = null
    var addTimerNavigator : AddTimerNavigator? = null
    var timerRepository : TimerRepository ?= null
}