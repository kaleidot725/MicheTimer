package kaleidot725.michetimer

import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.disptimer.DispTimerNavigator
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.main.MainNavigator
import kaleidot725.michetimer.domain.TimerRunnerService

lateinit var timerService : TimerRunnerService
lateinit var timerRepository : TimerRepository