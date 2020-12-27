package com.lab.esh1n.github.domain.events.usecase

import com.lab.esh1n.github.domain.UseCase
import com.lab.esh1n.github.domain.base.ErrorsHandler
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.PhotosRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchAndSavePhotosUseCase @Inject constructor(private val eventRepo: PhotosRepository, private val errorsHandler: ErrorsHandler)
    : UseCase<Int, Single<Resource<Unit>>> {

    override fun execute(argument: Int): Single<Resource<Unit>> {
        return eventRepo.fetchAndSaveEvents(argument)
                .andThen(Single.just(Resource.success()))
                .onErrorReturn { error -> Resource.error(errorsHandler.handle(error)) }
    }
}