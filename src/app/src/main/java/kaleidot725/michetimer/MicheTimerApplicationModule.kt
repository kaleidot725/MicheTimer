package kaleidot725.michetimer

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.disptimer.DispTimerActivity
import kaleidot725.michetimer.domain.FilePersistence
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import kaleidot725.michetimer.main.MainActivity
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import javax.inject.Singleton

@Module
class MicheTimerApplicationModule(application : Application) {
    private val application : Application = application

    @Provides
    @Singleton
    fun provideMicheTimerApplication() = application

    @Singleton
    @Provides
    fun provideTimerRunnerService() : TimerRunnerService {
        return TimerRunnerService(application)
    }

    @Singleton
    @Provides
    fun provideTimerRepository() : TimerRepository {
        val f = application.filesDir.path + "setting.json"
        val p = FilePersistence(f, Timer::class.java)
        return TimerRepository(p)
    }
}

@Singleton
@Component(modules = [MicheTimerApplicationModule::class])
interface MicheTimerApplicationComponent {
    fun inject(activity : MainActivity)
    fun inject(activity : AddTimerActivity)
    fun inject(activity : DispTimerActivity)
    fun inject(activity : StopTimerActivity)
}