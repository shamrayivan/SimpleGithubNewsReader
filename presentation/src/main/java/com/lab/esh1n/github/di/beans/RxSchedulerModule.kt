package com.lab.esh1n.github.di.beans

import com.lab.esh1n.github.base.BaseSchedulerProvider
import com.lab.esh1n.github.base.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RxSchedulersModule {
    @Provides
    @Singleton
    fun provideScheduleProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

}