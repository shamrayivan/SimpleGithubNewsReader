package com.lab.esh1n.github.domain.events.usecase

import androidx.paging.PagedList
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetEventsInDBUseCase @Inject constructor(private val eventsRepo: EventsRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<PagedList.BoundaryCallback<EventEntity>, Flowable<Resource<PagedList<EventEntity>>>> {
    override fun execute(argument: PagedList.BoundaryCallback<EventEntity>): Flowable<Resource<PagedList<EventEntity>>> {
        return eventsRepo.loadEvents(argument)
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
