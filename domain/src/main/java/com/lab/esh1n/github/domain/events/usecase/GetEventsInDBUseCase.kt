package com.lab.esh1n.github.domain.events.usecase

import androidx.paging.PagedList
import com.shamray.lab.cache.entity.PhotoEntity
import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.PhotosRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetEventsInDBUseCase @Inject constructor(private val photosRepo: PhotosRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<PagedList.BoundaryCallback<PhotoEntity>, Flowable<Resource<PagedList<PhotoEntity>>>> {
    override fun execute(argument: PagedList.BoundaryCallback<PhotoEntity>): Flowable<Resource<PagedList<PhotoEntity>>> {
        return photosRepo.loadEvents(argument)
                .map { events -> Resource.success(events) }
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}
