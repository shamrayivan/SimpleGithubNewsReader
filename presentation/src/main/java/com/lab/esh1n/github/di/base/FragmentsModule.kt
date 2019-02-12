package com.lab.esh1n.github.di.base


import com.lab.esh1n.github.search.mvp.SearchRepositoryMVPFragment
import com.lab.esh1n.github.search.mvvm.SearchRepositoryMVVMFragment
import com.lab.esh1n.github.di.search.SearchRepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by esh1n on 3/9/18.
 */

@Module
interface FragmentsModule {

    @ContributesAndroidInjector(modules = [SearchRepositoryModule::class])
    fun buildSearchMVVMFragment(): SearchRepositoryMVVMFragment

    @ContributesAndroidInjector(modules = [SearchRepositoryModule::class])
    fun buildSearchMVPFragment(): SearchRepositoryMVPFragment
}