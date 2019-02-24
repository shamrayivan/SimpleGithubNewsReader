package com.lab.esh1n.github.di.base

import android.app.Application
import android.content.Context
import com.lab.esh1n.github.base.ErrorDescriptionProviderImpl
import com.lab.esh1n.github.domain.base.ErrorDescriptionProvider
import com.lab.esh1n.github.domain.base.ErrorsHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    internal fun provideErrorDescriptionProvider(application: Application): ErrorDescriptionProvider {
        return ErrorDescriptionProviderImpl(application.resources)
    }


    @Provides
    @Singleton
    fun provideErrorHandler(errorDescriptionProvider: ErrorDescriptionProvider): ErrorsHandler {
        return ErrorsHandler(errorDescriptionProvider)
    }


}