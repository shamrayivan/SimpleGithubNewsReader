package com.lab.esh1n.github.di.beans

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.github.domain.events.EventsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun providePlaceRepository(userSessionApiService: APIService, database: GithubDB): EventsRepository {
        return EventsRepository(userSessionApiService, database)
    }

}
