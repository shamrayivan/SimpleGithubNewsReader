package com.lab.esh1n.github.events.mvvm

import androidx.lifecycle.MutableLiveData
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsInDBUseCase
import com.lab.esh1n.github.domain.events.FetchAndSaveEventsUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import com.lab.esh1n.github.utils.applyAndroidSchedulers
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class EventsViewModel
@Inject
constructor(private val loadEventsUseCase: EventsInDBUseCase,
            private val fetchAndSaveEventsUseCase: FetchAndSaveEventsUseCase)
    : BaseViewModel() {

    val events = MutableLiveData<Resource<List<EventModel>>>()
    private val eventModelMapper = EventModelMapper()

    fun loadEvents() {
        events.postValue(Resource.loading())
        addDisposable(
                loadEventsUseCase.execute(Unit)
                        .map { return@map Resource.map(it, eventModelMapper::map) }
                        .applyAndroidSchedulers()
                        .subscribe { models -> events.postValue(models) })

        addDisposable(
                fetchAndSaveEventsUseCase.execute(Unit)
                        .applyAndroidSchedulers()
                        .subscribe { models -> })
    }

}