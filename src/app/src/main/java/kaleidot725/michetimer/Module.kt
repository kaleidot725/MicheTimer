//package kaleidot725.michetimer
//
//import android.app.Application
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//class AppModule(private val application: Application) {
//
//    @Provides
//    fun provideContext(): Context {
//        return application
//    }
//
//    @Provides
//    @Singleton
//    fun privideTimerService(): PersonPresenter {
//        return PersonPresenter()
//    }
//}