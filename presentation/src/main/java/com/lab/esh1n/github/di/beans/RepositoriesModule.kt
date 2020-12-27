package com.lab.esh1n.github.di.beans

import com.shamray.lab.api.APIService
import com.shamray.lab.cache.GithubDB
import com.lab.esh1n.github.domain.events.PhotosRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun providePlaceRepository(userSessionApiService: APIService, database: GithubDB): PhotosRepository {
        return PhotosRepository(userSessionApiService, database)
    }

}
