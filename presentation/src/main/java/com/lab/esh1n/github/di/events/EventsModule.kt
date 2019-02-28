package com.lab.esh1n.github.di.events

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.events.FetchAndSaveEventsUseCase
import com.lab.esh1n.github.domain.events.GetEventUseCase
import com.lab.esh1n.github.domain.events.GetEventsInDBUseCase
import dagger.Module
import dagger.Provides

@Module
class EventsModule {

    @Provides
    fun provideEventsInDBUseCase(db: GithubDB, errorsHandler: ErrorsHandler): GetEventsInDBUseCase {
        return GetEventsInDBUseCase(db.repositoriesDAO(), errorsHandler)
    }

    @Provides
    fun provideFetchAndSaveEventsUseCase(db: GithubDB, api: APIService, errorsHandler: ErrorsHandler): FetchAndSaveEventsUseCase {
        return FetchAndSaveEventsUseCase(api, db.repositoriesDAO(), errorsHandler)
    }

    @Provides
    fun provideEventDetailsUseCase(db: GithubDB, errorsHandler: ErrorsHandler): GetEventUseCase {
        return GetEventUseCase(db.repositoriesDAO(), errorsHandler)
    }
}