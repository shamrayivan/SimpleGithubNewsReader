package com.lab.esh1n.github.domain.events.usecase

import androidx.paging.PagedList
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.EventsRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class LikeEventUseCase @Inject constructor(private val eventRepo: EventsRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<EventEntity, Single<Resource<Unit>>> {

    override fun execute(argument: EventEntity): Single<Resource<Unit>> {
        return eventRepo.changeLikeStatus(argument)
                .andThen(Single.just(Resource.success()))
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}