package com.lab.esh1n.github.search.mvp

import android.app.Application
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.model.ErrorModel
import com.lab.esh1n.github.domain.search.FetchAndSaveRepositoriesUseCase
import com.lab.esh1n.github.domain.search.SearchRepositoriesInDBUseCase
import com.lab.esh1n.github.search.RepositoryModel
import com.lab.esh1n.github.utils.applyAndroidSchedulers
import io.reactivex.Observable
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.*
import javax.inject.Inject

class SearchRepositoryPresenter
@Inject
constructor(
        private val application: Application,
        private val searchReposUseCase: SearchRepositoriesInDBUseCase,
        private val fetchAndSaveRepositoriesUseCase: FetchAndSaveRepositoriesUseCase
) : SearchContract.Presenter() {
    private lateinit var query: String

    override fun search(query: String) {
        this.query = query
        subscriptions.clear()

        view?.showProgress()

        val searchResult = searchReposUseCase.execute(query)
        unSubscribeOnDestroy(
                searchResult
                        .map { repos ->
                            val repositoryModels = ArrayList<RepositoryModel>()
                            for (entity in repos) {
                                repositoryModels.add(
                                        RepositoryModel(
                                                id = entity.id,
                                                name = entity.name,
                                                language = entity.language,
                                                forksCount = entity.forksCount,
                                                watchersCount = entity.watchCount,
                                                starsCount = entity.starsCount,
                                                avatar = entity.ownerAvatar)
                                )
                            }
                            repositoryModels
                        }
                        .applyAndroidSchedulers()
                        .subscribe { models ->
                            if (models.isEmpty()) {
                                view?.onNoResults()
                            } else {
                                view?.hideProgress()
                                view?.showRepositories(models)
                            }
                        }
        )

        unSubscribeOnDestroy(
                searchResult
                        .toObservable()
                        .flatMap<Boolean> {
                            if (it.isEmpty()) {
                                return@flatMap fetchAndSaveRepositoriesUseCase.execute(query)
                            } else return@flatMap Observable.never()
                        }
                        .applyAndroidSchedulers()
                        .subscribe(
                                handleRepositoriesLoadingSuccessState(),
                                handleRepositoriesLoadingErrorState()
                        )
        )

    }

    override fun loadMore() {
        view?.showProgress()

        unSubscribeOnDestroy(
                fetchAndSaveRepositoriesUseCase.execute(query)
                        .applyAndroidSchedulers()
                        .subscribe(handleRepositoriesLoadingSuccessState(), handleRepositoriesLoadingErrorState())
        )
    }

    private fun handleRepositoriesLoadingErrorState(): (Throwable) -> Unit {
        return {
            view?.hideProgress()
            if (it is HttpException && it.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                view?.onError(ErrorModel.httpError(application.getString(R.string.error_rate_limit), it.code()))
            } else {
                view?.onError(ErrorModel.unexpectedError(application.getString(R.string.error_unexpected)))
            }
        }
    }

    private fun handleRepositoriesLoadingSuccessState(): (Boolean) -> Unit {
        return {
            if (it) {
                view?.onAllItemsLoaded()
            }
            view?.hideProgress()
        }
    }

}