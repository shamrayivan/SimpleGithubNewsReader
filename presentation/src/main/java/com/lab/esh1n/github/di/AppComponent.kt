package com.lab.esh1n.github.di

import android.app.Application
import com.lab.esh1n.github.GithubApp
import com.lab.esh1n.github.di.base.*
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
    ViewModelModule::class])
interface AppComponent {
    fun inject(app: GithubApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
