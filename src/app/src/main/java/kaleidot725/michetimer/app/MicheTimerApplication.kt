package kaleidot725.michetimer.app

import android.app.Application
import dagger.internal.DaggerCollections
import kaleidot725.michetimer.DaggerMicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationComponent
import kaleidot725.michetimer.MicheTimerApplicationModule
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import javax.inject.Inject

class MicheTimerApplication : Application() {

    lateinit var component : MicheTimerApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerMicheTimerApplicationComponent.builder().micheTimerApplicationModule(MicheTimerApplicationModule(this)).build()
    }
}