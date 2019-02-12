package com.lab.esh1n.github.search.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lab.esh1n.github.domain.search.FetchAndSaveRepositoriesUseCase
import com.lab.esh1n.github.domain.search.SearchRepositoriesInDBUseCase
import com.lab.esh1n.github.search.RepositoryModel
import com.lab.esh1n.github.utils.applyAndroidSchedulers
import com.lab.esh1n.github.GithubApp
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.model.ErrorModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.*
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class SearchRepositoryViewModel
@Inject
constructor(application: Application,
            private val searchReposUseCase: SearchRepositoriesInDBUseCase,
            private val fetchAndSaveRepositoriesUseCase: FetchAndSaveRepositoriesUseCase)
    : AndroidViewModel(application) {

    private val searchResultState = MutableLiveData<List<RepositoryModel>>()
    private val loadingState = MutableLiveData<Boolean>()
    private val allItemsLoadedState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<ErrorModel>()

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private lateinit var query: String

    fun observeSearchResult(): LiveData<List<RepositoryModel>> {
        return searchResultState
    }

    fun observeLoadingState(): LiveData<Boolean> {
        return loadingState
    }

    fun observeAllItemsLoadedState(): LiveData<Boolean> {
        return allItemsLoadedState
    }

    fun observeErrorState(): LiveData<ErrorModel> {
        return errorState
    }

    fun search(query: String) {
        this.query = query
        subscriptions.clear()

        loadingState.postValue(true)

        val searchResult = searchReposUseCase.execute(query)

        subscriptions.add(
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
                            searchResultState.postValue(models)
                            if (models.isNotEmpty()) {
                                loadingState.postValue(false)
                            }
                        }
        )

        subscriptions.add(
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

    fun loadMore() {
        loadingState.postValue(true)
        subscriptions.add(
                fetchAndSaveRepositoriesUseCase.execute(query)
                        .applyAndroidSchedulers()
                        .subscribe(
                                handleRepositoriesLoadingSuccessState(),
                                handleRepositoriesLoadingErrorState())
        )
    }

    private fun handleRepositoriesLoadingErrorState(): (Throwable) -> Unit {
        return {
            loadingState.postValue(false)
            if (it is HttpException && it.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                errorState.postValue(ErrorModel.httpError(getApplication<GithubApp>().getString(R.string.error_rate_limit), it.code()))
            } else {
                errorState.postValue(ErrorModel.unexpectedError(getApplication<GithubApp>().getString(R.string.error_unexpected)))
            }
        }
    }

    private fun handleRepositoriesLoadingSuccessState(): (Boolean) -> Unit {
        return {
            if (it) {
                allItemsLoadedState.postValue(it)
            }
            loadingState.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}