package com.lab.esh1n.github.domain.events

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.events.mapper.EventResponseMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable

class EventsRepository(private val apiService: APIService, db: GithubDB) {

    private val eventResponseMapper = EventResponseMapper()
    private val eventDao = db.eventsDAO();
    fun getEventById(id: Long): Flowable<EventEntity> {
        return eventDao.getEventById(id)
    }

    fun loadEvents(boundaryCallback: PagedList.BoundaryCallback<EventEntity>): Flowable<PagedList<EventEntity>> {

        val dataSourceFactory = eventDao.getEvents()
        return RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .buildFlowable(BackpressureStrategy.BUFFER)
    }

    fun fetchAndSaveEvents(page: Int): Completable {
        return apiService
                .getEvents("esh1n", page)
                .map {
                    eventResponseMapper.map(it)
                }
                .flatMapCompletable { events ->
                    Completable.fromAction {
                        eventDao.saveEvents(events)
                    }
                }
    }

    fun changeLikeStatus(eventEntity: EventEntity) =
            eventDao.setLikeToEvent(eventEntity.id, !eventEntity.like)

    fun fetchAndSaveNewEvents(): Completable {
        return fetchAndSaveEvents(START_PAGE)
    }

    companion object {
        const val DATABASE_PAGE_SIZE = 30
        const val START_PAGE = 1
    }

}