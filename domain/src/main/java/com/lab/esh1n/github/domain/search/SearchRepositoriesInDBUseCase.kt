package com.lab.esh1n.github.domain.search

import com.lab.esh1n.data.cache.dao.RepositoriesDAO
import com.lab.esh1n.data.cache.entity.RepositoryEntity
import com.lab.esh1n.github.domain.UseCase
import io.reactivex.Flowable

class SearchRepositoriesInDBUseCase(private val reposoriesDAO : RepositoriesDAO )
    : UseCase<String, Flowable<List<RepositoryEntity>>> {
    override fun execute(argument: String): Flowable<List<RepositoryEntity>> {
        return reposoriesDAO.searchRepository(argument)
    }
}
