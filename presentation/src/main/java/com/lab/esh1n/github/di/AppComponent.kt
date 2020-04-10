package com.lab.esh1n.github.di

import android.app.Application
import com.lab.esh1n.github.GithubApp
import com.lab.esh1n.github.di.beans.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by esh1n on 3/7/18.
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    ActivitiesModule::class,
    ViewModelModule::class,
    RxSchedulersModule::class])
interface AppComponent {
    fun inject(app: GithubApp)

    fun plusWorkerComponent(): WorkerComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
