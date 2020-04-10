package com.lab.esh1n.github.domain.events

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.dao.EventsDAO
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.mapper.EventResponseMapper
import io.reactivex.Completable
import io.reactivex.Single

class FetchAndSaveEventsUseCase(private val api: APIService, private val eventsDAO: EventsDAO, private val errorsHandler: ErrorsHandler)
    : UseCase<Unit, Single<Resource<Unit>>> {

    private val eventResponseMapper = EventResponseMapper()

    override fun execute(argument: Unit): Single<Resource<Unit>> {
        return api
                .getEvents("esh1n")
                .map {
                    eventResponseMapper.map(it)
                }
                .flatMapCompletable { events ->
                    Completable.fromAction {
                        eventsDAO.saveEvents(events)
                    }
                }
                .andThen(
                        Single.just(Resource.success()))
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }


    }
}