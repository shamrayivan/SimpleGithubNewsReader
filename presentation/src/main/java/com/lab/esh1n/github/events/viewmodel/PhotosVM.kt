package com.lab.esh1n.github.events.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.work.WorkManager
import com.shamray.lab.cache.entity.PhotoEntity
import com.lab.esh1n.github.base.BaseSchedulerProvider
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.base.applySchedulersFlowable
import com.lab.esh1n.github.base.applySchedulersSingle
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.PhotosRepository.Companion.START_PAGE
import com.lab.esh1n.github.domain.events.usecase.FetchAndSavePhotosUseCase
import com.lab.esh1n.github.domain.events.usecase.GetEventsInDBUseCase
import com.lab.esh1n.github.domain.events.usecase.LikeArgs
import com.lab.esh1n.github.domain.events.usecase.LikeEventUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import com.lab.esh1n.github.utils.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class PhotosVM
@Inject
constructor(private val loadEventsUseCase: GetEventsInDBUseCase,
            private val fetchAndSavePhotosUseCase: FetchAndSavePhotosUseCase,
            private val likeUseCase: LikeEventUseCase,
            private val baseSchedulerProvider: BaseSchedulerProvider,
            private val workManager: WorkManager)
    : BaseViewModel() {

    val events = MutableLiveData<Resource<PagedList<PhotoEntity>>>()
    val refreshOperation = SingleLiveEvent<Resource<Unit>>()
    val loadMoreOperation = SingleLiveEvent<Resource<Unit>>()
    val eventModelMapper: (PhotoEntity) -> EventModel = EventModelMapper()::map
    private val eventBoundaryCallback = EventBoundaryCallback()

    //TODO move this periodic sync to success login event and add scheduled start date via oneTimeRequest

    fun startPeriodicSync() {
        //workManager.startPeriodicSync()
    }

    fun loadEvents() {
        loadEventsUseCase.execute(eventBoundaryCallback)
                .doOnSubscribe { events.postValue(Resource.loading()) }
                .compose(baseSchedulerProvider.applySchedulersFlowable())
                .subscribe { models ->
                    events.postValue(models)
                }
                .disposeOnDestroy()
    }

    fun onChangeLikeState(photoEntity:PhotoEntity){
        likeUseCase.execute(LikeArgs(photoEntity.id,photoEntity.like))
                .compose(baseSchedulerProvider.applySchedulersSingle())
                .subscribe { result -> }
                .disposeOnDestroy()
    }

    fun refresh() {
        if (refreshOperation.value?.status == Resource.Status.LOADING) {
            return
        }
        fetchAndSavePhotosUseCase.execute(START_PAGE)
                .doOnSubscribe { _ ->
                    refreshOperation.postValue(Resource.loading())
                }
                .compose(baseSchedulerProvider.applySchedulersSingle())
                .subscribe { result ->
                    refreshOperation.postValue(result)
                }
                .disposeOnDestroy()
    }

    private inner class EventBoundaryCallback() : PagedList.BoundaryCallback<PhotoEntity>() {

        private var lastRequestedPage = START_PAGE

        // avoid triggering multiple requests in the same time
        private var isRequestInProgress = false
        override fun onZeroItemsLoaded() {
            requestAndSaveData()
        }

        override fun onItemAtEndLoaded(itemAtEnd: PhotoEntity) {
            requestAndSaveData()
        }

        private fun requestAndSaveData() {
            if (isRequestInProgress) return
            isRequestInProgress = true
            fetchAndSavePhotosUseCase.execute(lastRequestedPage)
                    .doOnSubscribe { _ ->
                        loadMoreOperation.postValue(Resource.loading())
                    }
                    .compose(baseSchedulerProvider.applySchedulersSingle())
                    .subscribe { result ->
                        lastRequestedPage++
                        isRequestInProgress = false
                        loadMoreOperation.postValue(result)
                    }
                    .disposeOnDestroy()
        }

    }


}