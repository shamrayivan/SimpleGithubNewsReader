package com.lab.esh1n.github.events.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkManager
import com.lab.esh1n.github.base.BaseSchedulerProvider
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.base.applySchedulersFlowable
import com.lab.esh1n.github.base.applySchedulersSingle
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.FetchAndSaveEventsUseCase
import com.lab.esh1n.github.domain.events.GetEventsInDBUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import com.lab.esh1n.github.utils.SingleLiveEvent
import com.lab.esh1n.github.utils.startPeriodicSync
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class EventsVM
@Inject
constructor(private val loadEventsUseCase: GetEventsInDBUseCase,
            private val fetchAndSaveEventsUseCase: FetchAndSaveEventsUseCase,
            private val baseSchedulerProvider: BaseSchedulerProvider,
            private val workManager: WorkManager)
    : BaseViewModel() {

    val events = MutableLiveData<Resource<List<EventModel>>>()
    val refreshOperation = SingleLiveEvent<Resource<Unit>>()
    private val eventModelMapper = EventModelMapper()

    //TODO move this periodic sync to success login event
    fun startPeriodicSync() {
        workManager.startPeriodicSync()
    }

    fun loadEvents() {
        loadEventsUseCase.execute(Unit)
                .doOnSubscribe { events.postValue(Resource.loading()) }
                .map { return@map Resource.map(it, eventModelMapper::map) }
                .compose(baseSchedulerProvider.applySchedulersFlowable())
                .subscribe { models -> events.postValue(models) }
                .disposeOnDestroy()
    }

    fun refresh() {
        fetchAndSaveEventsUseCase.execute(Unit)
                .doOnSubscribe { _ ->
                    refreshOperation.postValue(Resource.loading())
                }
                .compose(baseSchedulerProvider.applySchedulersSingle())
                .subscribe { result ->
                    refreshOperation.postValue(result)
                }
                .disposeOnDestroy()
    }

}