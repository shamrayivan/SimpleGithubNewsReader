package com.lab.esh1n.github.di.search

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.github.domain.search.FetchAndSaveRepositoriesUseCase
import com.lab.esh1n.github.domain.search.SearchRepositoriesInDBUseCase
import dagger.Module
import dagger.Provides

@Module
class SearchRepositoryModule {

    @Provides
    fun provideSearchRepositoriesInDBUseCase(db: GithubDB): SearchRepositoriesInDBUseCase {
        return SearchRepositoriesInDBUseCase(db.repositoriesDAO())
    }

    @Provides
    fun provideFetchAndSaveRepositoriesUseCase(db: GithubDB, api: APIService): FetchAndSaveRepositoriesUseCase {
        return FetchAndSaveRepositoriesUseCase(api, db.repositoriesDAO())
    }
}