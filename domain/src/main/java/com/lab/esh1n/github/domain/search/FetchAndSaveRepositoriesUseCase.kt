package com.lab.esh1n.github.domain.search

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.api.response.RepositoryResponse
import com.lab.esh1n.data.api.response.SearchResponse
import com.lab.esh1n.data.cache.dao.RepositoriesDAO
import com.lab.esh1n.data.cache.entity.RepositoryEntity
import com.lab.esh1n.github.domain.UseCase
import io.reactivex.Observable
import retrofit2.HttpException
import retrofit2.Response
import java.text.ParseException
import java.util.*

class FetchAndSaveRepositoriesUseCase(private val api: APIService, private val repositoriesDAO : RepositoriesDAO)
    : UseCase<String, Observable<Boolean>> {

    private var paginationInfo: SearchResponse.PaginationInfo? = null
    private var currentPage = 1

    override fun execute(argument: String): Observable<Boolean> {
        currentPage = paginationInfo?.nextPage ?: 1

        return api.searchRepositories(argument, currentPage)
                .map{ handleSearchResponse(it, argument) }
    }


    private fun handleSearchResponse(response: Response<SearchResponse<RepositoryResponse>>,
                                     query: String): Boolean {
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        paginationInfo = SearchResponse.getPaginationInfo(response)

        val repositoryResponseList = response.body()?.items
        val entities = ArrayList<RepositoryEntity>()
        if (repositoryResponseList != null) {
            for (repositoryResponse in repositoryResponseList) {
                try {
                    val repositoryEntity = RepositoryEntity(
                            id = repositoryResponse.id,
                            name = repositoryResponse.name,
                            ownerId = repositoryResponse.owner?.login,
                            ownerAvatar = repositoryResponse.owner?.avatar,
                            description = repositoryResponse.description,
                            forksCount = repositoryResponse.forksCount,
                            watchCount = repositoryResponse.watchCount,
                            starsCount = repositoryResponse.starsCount,
                            language = repositoryResponse.language,
                            order = System.currentTimeMillis(),
                            query = query.toLowerCase())
                    entities.add(repositoryEntity)
                } catch (e: ParseException) {
                    throw RuntimeException(e)
                }

            }
        }

        repositoriesDAO.saveRepositories(entities)

        return currentPage == paginationInfo?.lastPage
    }
}