package com.lab.esh1n.github.domain.events.usecase

import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetEventUseCase @Inject constructor(private val eventsRepo: EventsRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<Long, Flowable<Resource<EventEntity>>> {
    override fun execute(argument: Long): Flowable<Resource<EventEntity>> {
        return eventsRepo.getEventById(argument)
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
