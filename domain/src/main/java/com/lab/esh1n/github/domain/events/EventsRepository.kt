package com.lab.esh1n.github.domain.events

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.events.usecase.LikeArgs
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class EventsRepository(private val apiService: APIService, db: GithubDB) {

    private val eventDao = db.eventsDAO();
    fun getEventById(id: Long): Flowable<EventEntity> {
        return eventDao.getEventById(id)
    }

    fun loadEvents(boundaryCallback: PagedList.BoundaryCallback<EventEntity>): Flowable<PagedList<EventEntity>> {

        val dataSourceFactory = eventDao.getEvents()
        return RxPagedListBuilder(dataSourceFactory, PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .buildFlowable(BackpressureStrategy.BUFFER)
    }

    fun fetchAndSaveEvents(page: Int): Completable {
        return Single.fromCallable {
            createPhotos(page)
        }.flatMapCompletable { events ->
            Completable.fromAction {
                eventDao.saveEvents(events)
            }
        }
    }

    private fun createPhotos(page: Int) =
            (0 until PAGE_SIZE).map { page * PAGE_SIZE + it }.map { id ->
                val url = "https://picsum.photos/id/$id/800/"
                EventEntity(id.toLong(), url)
            }

    fun changeLikeStatus(eventEntity: LikeArgs) =
            eventDao.setLikeToEvent(eventEntity.id, !eventEntity.isLiked)

    fun fetchAndSaveNewEvents(): Completable {
        return fetchAndSaveEvents(START_PAGE)
    }

    companion object {
        const val PAGE_SIZE = 20
        const val START_PAGE = 1
    }

}