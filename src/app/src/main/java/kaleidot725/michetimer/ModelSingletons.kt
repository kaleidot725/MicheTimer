package kaleidot725.michetimer

import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.main.MicheTimerNavigator
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.service.TimerRunnerService

var micheTimerNavigator : MicheTimerNavigator? = null
var addTimerNavigator : AddTimerNavigator? = null
var timerService : TimerRunnerService?= null
var timerRepository : TimerRepository?= null