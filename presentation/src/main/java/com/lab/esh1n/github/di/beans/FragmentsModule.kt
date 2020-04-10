package com.lab.esh1n.github.di.beans


import com.lab.esh1n.github.di.events.EventsModule
import com.lab.esh1n.github.events.fragment.EventDetailFragment
import com.lab.esh1n.github.events.fragment.EventsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by esh1n on 3/9/18.
 */

@Module
interface FragmentsModule {

    @ContributesAndroidInjector(modules = [EventsModule::class])
    fun buildEventsFragment(): EventsFragment

    @ContributesAndroidInjector(modules = [EventsModule::class])
    fun buildEventDetailFragment(): EventDetailFragment

}