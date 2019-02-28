package com.lab.esh1n.github.domain.events

import com.lab.esh1n.data.cache.dao.EventsDAO
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import io.reactivex.Flowable

class GetEventUseCase(private val eventsDAO: EventsDAO, private val errorsHandler: ErrorsHandler)
    : UseCase<Long, Flowable<Resource<EventEntity>>> {
    override fun execute(argument: Long): Flowable<Resource<EventEntity>> {
        return eventsDAO.getEventById(argument)
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
