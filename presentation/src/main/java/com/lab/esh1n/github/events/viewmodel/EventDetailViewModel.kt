package com.lab.esh1n.github.events.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lab.esh1n.github.base.BaseSchedulerProvider
import com.lab.esh1n.github.base.BaseViewModel
import com.lab.esh1n.github.base.applySchedulersFlowable
import com.lab.esh1n.github.base.applySchedulersSingle
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.usecase.GetEventUseCase
import com.lab.esh1n.github.domain.events.usecase.LikeArgs
import com.lab.esh1n.github.domain.events.usecase.LikeEventUseCase
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.mapper.EventModelMapper
import javax.inject.Inject


class EventDetailViewModel
@Inject
constructor(private val loadEventUseCase: GetEventUseCase, private val likeUseCase: LikeEventUseCase, private val baseSchedulerProvider: BaseSchedulerProvider) : BaseViewModel() {

    val event = MutableLiveData<Resource<EventModel>>()
    private val eventModelMapper = EventModelMapper()

    fun loadEvent(id: Long) {
        loadEventUseCase.execute(id)
                .doOnSubscribe { event.postValue(Resource.loading()) }
                .map { return@map Resource.map(it, eventModelMapper::map) }
                .compose(baseSchedulerProvider.applySchedulersFlowable())
                .subscribe { models -> event.postValue(models) }
                .disposeOnDestroy()
    }

    fun onChangeLikeState() {
        event.value?.data?.let{
            likeUseCase.execute(LikeArgs(it.id,it.isLiked))
                    .compose(baseSchedulerProvider.applySchedulersSingle())
                    .subscribe { result -> }
                    .disposeOnDestroy()
        }

    }

}