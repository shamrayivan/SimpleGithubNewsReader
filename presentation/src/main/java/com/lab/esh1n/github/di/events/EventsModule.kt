package com.lab.esh1n.github.di.events

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.events.EventsInDBUseCase
import com.lab.esh1n.github.domain.events.FetchAndSaveEventsUseCase
import dagger.Module
import dagger.Provides

@Module
class EventsModule {

    @Provides
    fun provideSearchRepositoriesInDBUseCase(db: GithubDB, errorsHandler: ErrorsHandler): EventsInDBUseCase {
        return EventsInDBUseCase(db.repositoriesDAO(), errorsHandler)
    }

    @Provides
    fun provideFetchAndSaveEventsUseCase(db: GithubDB, api: APIService, errorsHandler: ErrorsHandler): FetchAndSaveEventsUseCase {
        return FetchAndSaveEventsUseCase(api, db.repositoriesDAO(), errorsHandler)
    }
}