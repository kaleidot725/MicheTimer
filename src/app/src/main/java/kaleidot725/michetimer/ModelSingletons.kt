package kaleidot725.michetimer

import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.disptimer.DispTimerNavigator
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.main.MainNavigator
import kaleidot725.michetimer.service.TimerRunnerService

var mainNavigator : MainNavigator? = null
var addTimerNavigator : AddTimerNavigator? = null
var dispTimerNavigator : DispTimerNavigator? = null
var timerService : TimerRunnerService?= null
var timerRepository : TimerRepository?= null