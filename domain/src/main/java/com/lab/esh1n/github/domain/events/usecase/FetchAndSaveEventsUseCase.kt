package com.lab.esh1n.github.domain.events.usecase

import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchAndSaveEventsUseCase @Inject constructor(private val eventRepo: EventsRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<Unit, Single<Resource<Unit>>> {

    override fun execute(argument: Unit): Single<Resource<Unit>> {
        return eventRepo.refreshEvents()
                .andThen(Single.just(Resource.success()))
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}