package kaleidot725.michetimer.app

import android.app.Application
import kaleidot725.michetimer.DaggerMicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationModule

class MicheTimerApplication : Application() {

    lateinit var component : MicheTimerApplicationComponent
    lateinit var module : MicheTimerApplicationModule

    override fun onCreate() {
        super.onCreate()
        module  = MicheTimerApplicationModule(this)
        component = DaggerMicheTimerApplicationComponent.builder().micheTimerApplicationModule(module).build()
    }
}