package com.lab.esh1n.github.domain.events

import com.lab.esh1n.data.cache.dao.EventsDAO
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import io.reactivex.Flowable

class EventsInDBUseCase(private val eventsDAO: EventsDAO, private val errorsHandler: ErrorsHandler)
    : UseCase<Any, Flowable<Resource<List<EventEntity>>>> {
    override fun execute(argument: Any): Flowable<Resource<List<EventEntity>>> {
        return eventsDAO.getEvents()
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
