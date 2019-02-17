package kaleidot725.michetimer

import android.app.Application
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kaleidot725.michetimer.addtimer.AddTimerActivity
import kaleidot725.michetimer.addtimer.AddTimerFragment
import kaleidot725.michetimer.addtimer.AddTimerMode
import kaleidot725.michetimer.addtimer.AddTimerNavigator
import kaleidot725.michetimer.app.MicheTimerApplication
import kaleidot725.michetimer.disptimer.DispTimerActivity
import kaleidot725.michetimer.disptimer.DispTimerFragment
import kaleidot725.michetimer.disptimer.DispTimerNavigator
import kaleidot725.michetimer.model.domain.alarm.AlarmType
import kaleidot725.michetimer.model.domain.timer.TimerRunnerService
import kaleidot725.michetimer.model.repository.PersistenceFile
import kaleidot725.michetimer.model.entity.Timer
import kaleidot725.michetimer.model.repository.TimerRepository
import kaleidot725.michetimer.main.MainActivity
import kaleidot725.michetimer.main.MainFilter
import kaleidot725.michetimer.main.MainFragment
import kaleidot725.michetimer.main.MainNavigator
import kaleidot725.michetimer.stoptimer.StopTimerActivity
import javax.inject.Named
import javax.inject.Singleton

@Module
class MicheTimerApplicationModule(application : Application) {
    private val application : Application = application

    @Singleton
    @Provides
    fun provideTimerRunnerService() : TimerRunnerService {
        return TimerRunnerService(application)
    }

    @Singleton
    @Provides
    fun provideTimerRepository() : TimerRepository {
        val f = application.filesDir.path + "setting.json"
        val p = PersistenceFile(f, Timer::class.java)
        return TimerRepository(p)
    }

    @Singleton
    @Provides @Named(value = "DispTimer")
    fun provideDispTimer() : Timer {
        return Timer(-1, "", 0, AlarmType.Bellstar)
    }

    @Singleton
    @Provides @Named("EditTimer")
    fun provideEditTimer() : Timer {
        return Timer(-1, "", 0, AlarmType.Bellstar)
    }

    @Singleton
    @Provides
    fun provideAddTimerMode() : AddTimerMode {
        return AddTimerMode(AddTimerMode.add)
    }

    @Singleton
    @Provides
    fun provideMainFilter() : MainFilter {
        return MainFilter.None
    }
}

@Module
class MainActivityModule(activity : MainActivity)
{
    private val activity : MainActivity = activity

    @Provides
    fun provideMainNavigator() : MainNavigator{
        return activity
    }
}

@Module
class AddTimerActivityModule(activity : AddTimerActivity)
{
    private val activity : AddTimerActivity = activity

    @Provides
    fun provideAddTimerNavigator() : AddTimerNavigator{
        return activity
    }
}

@Module
class DispTimerActivityModule(activity : DispTimerActivity)
{
    private val activity : DispTimerActivity = activity

    @Provides
    fun provideNavigator() : DispTimerNavigator{
        return activity
    }
}

@Module
class StopTimerActivityModule()
{

}


@Module
class MainFragmentModule{
}

@Module
class AddTimerFragmentModule {

}

@Module
class DispTimerFragmentModule{

}

@Singleton
@Component(modules = [MicheTimerApplicationModule::class])
interface MicheTimerApplicationComponent {
    fun inject(application : MicheTimerApplication)
    fun plus(module : MainActivityModule) : MainActivityComponent
    fun plus(module : AddTimerActivityModule) : AddTimerActivityComponent
    fun plus(module : DispTimerActivityModule) : DispTimerActivityComponent
    fun plus(module : StopTimerActivityModule) : StopTimerActivityComponent
}

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity : MainActivity)
    fun plus(fragment : MainFragmentModule) : MainFragmentComponent
}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    fun inject(fragment : MainFragment)
}

@Subcomponent(modules = [AddTimerActivityModule::class])
interface AddTimerActivityComponent {
    fun inject(activity : AddTimerActivity)
    fun plus(fragment : AddTimerFragmentModule) : AddTimerFragmentComponent
}

@Subcomponent(modules = [AddTimerFragmentModule::class])
interface AddTimerFragmentComponent {
    fun inject(fragment : AddTimerFragment)
}

@Subcomponent(modules = [DispTimerActivityModule::class])
interface DispTimerActivityComponent {
    fun inject(activity : DispTimerActivity)
    fun plus(fragment : DispTimerFragmentModule) : DispTimerFragmentComponent
}

@Subcomponent(modules = [DispTimerFragmentModule::class])
interface  DispTimerFragmentComponent {
    fun inject(fragment : DispTimerFragment)
}

@Subcomponent(modules = [StopTimerActivityModule::class])
interface StopTimerActivityComponent {
    fun inject(activity : StopTimerActivity)
}
