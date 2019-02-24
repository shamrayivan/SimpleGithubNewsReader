package com.lab.esh1n.github.di.base

import com.lab.esh1n.github.events.EventsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by esh1n on 3/9/18.
 */
@Module
interface ActivitiesModule {
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    fun contributeHomeActivity(): EventsActivity
}