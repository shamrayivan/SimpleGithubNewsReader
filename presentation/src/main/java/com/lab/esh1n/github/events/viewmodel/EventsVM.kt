package com.lab.esh1n.github.events.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.work.WorkManager
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.base.BaseSchedulerProvider
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.base.applySchedulersFlowable
import com.lab.esh1n.github.base.applySchedulersSingle
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsRepository.Companion.START_PAGE
import com.lab.esh1n.github.domain.events.usecase.FetchAndSaveEventsUseCase
import com.lab.esh1n.github.domain.events.usecase.GetEventsInDBUseCase
import com.lab.esh1n.github.domain.events.usecase.LikeEventUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import com.lab.esh1n.github.utils.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class EventsVM
@Inject
constructor(private val loadEventsUseCase: GetEventsInDBUseCase,
            private val fetchAndSaveEventsUseCase: FetchAndSaveEventsUseCase,
            private val likeUseCase: LikeEventUseCase,
            private val baseSchedulerProvider: BaseSchedulerProvider,
            private val workManager: WorkManager)
    : BaseViewModel() {

    val events = MutableLiveData<Resource<PagedList<EventEntity>>>()
    val refreshOperation = SingleLiveEvent<Resource<Unit>>()
    val loadMoreOperation = SingleLiveEvent<Resource<Unit>>()
    val eventModelMapper: (EventEntity) -> EventModel = EventModelMapper()::map
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

    fun onChangeLikeState(eventEntity:EventEntity){
        likeUseCase.execute(eventEntity)
                .compose(baseSchedulerProvider.applySchedulersSingle())
                .subscribe { result -> }
                .disposeOnDestroy()
    }

    fun refresh() {
        if (refreshOperation.value?.status == Resource.Status.LOADING) {
            return
        }
        fetchAndSaveEventsUseCase.execute(START_PAGE)
                .doOnSubscribe { _ ->
                    refreshOperation.postValue(Resource.loading())
                }
                .compose(baseSchedulerProvider.applySchedulersSingle())
                .subscribe { result ->
                    refreshOperation.postValue(result)
                }
                .disposeOnDestroy()
    }

    private inner class EventBoundaryCallback() : PagedList.BoundaryCallback<EventEntity>() {

        private var lastRequestedPage = START_PAGE

        // avoid triggering multiple requests in the same time
        private var isRequestInProgress = false
        override fun onZeroItemsLoaded() {
            requestAndSaveData()
        }

        override fun onItemAtEndLoaded(itemAtEnd: EventEntity) {
            requestAndSaveData()
        }

        private fun requestAndSaveData() {
            if (isRequestInProgress) return
            isRequestInProgress = true
            fetchAndSaveEventsUseCase.execute(lastRequestedPage)
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