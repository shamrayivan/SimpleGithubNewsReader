package com.lab.esh1n.github.domain.events

import com.lab.esh1n.data.api.APIService
import com.lab.esh1n.data.cache.GithubDB
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.events.mapper.EventResponseMapper
import io.reactivex.Completable
import io.reactivex.Flowable

class EventsRepository(private val apiService: APIService, db: GithubDB) {

    private val eventResponseMapper = EventResponseMapper()
    private val eventDao = db.eventsDAO();
    fun getEventById(id: Long): Flowable<EventEntity> {
        return eventDao.getEventById(id)
    }

    fun getEvents(): Flowable<List<EventEntity>> {
        return eventDao.getEvents()
    }

    fun refreshEvents(): Completable {
        return apiService
                .getEvents("esh1n", 1)
                .map {
                    eventResponseMapper.map(it)
                }
                .flatMapCompletable { events ->
                    Completable.fromAction {
                        eventDao.saveEvents(events)
                    }
                }
    }

}