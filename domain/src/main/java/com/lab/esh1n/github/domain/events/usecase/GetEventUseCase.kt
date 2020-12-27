package com.lab.esh1n.github.domain.events.usecase

import com.shamray.lab.cache.entity.PhotoEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.PhotosRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetEventUseCase @Inject constructor(private val photosRepo: PhotosRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<Long, Flowable<Resource<PhotoEntity>>> {
    override fun execute(argument: Long): Flowable<Resource<PhotoEntity>> {
        return photosRepo.getEventById(argument)
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
