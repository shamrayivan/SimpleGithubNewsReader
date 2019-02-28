package com.lab.esh1n.github.events.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.GetEventUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import com.lab.esh1n.github.utils.applyAndroidSchedulers
import javax.inject.Inject


class EventDetailViewModel
@Inject
constructor(private val loadEventUseCase: GetEventUseCase) : BaseViewModel() {

    val event = MutableLiveData<Resource<EventModel>>()
    private val eventModelMapper = EventModelMapper()

    fun loadEvent(id: Long) {
        event.postValue(Resource.loading())
        addDisposable(
                loadEventUseCase.execute(id)
                        .map { return@map Resource.map(it, eventModelMapper::map) }
                        .applyAndroidSchedulers()
                        .subscribe { models
                            ->
                            event.postValue(models)
                        })
    }

}