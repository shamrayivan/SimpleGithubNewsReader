package com.lab.esh1n.github.domain.events.mapper

import com.lab.esh1n.data.api.response.EventResponse
import com.lab.esh1n.data.cache.DBTypeConverters
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.base.Mapper
import java.util.*

class EventResponseMapper : Mapper<EventResponse, EventEntity>() {

    private val dateConverter = DBTypeConverters()

    override fun map(source: EventResponse): EventEntity {

        return EventEntity(
                id = source.id,
                type = source.type ?: "",
                repositoryName = source.repositoryResponse?.name ?: "",
                actorName = source.actorResponse?.displayLogin ?: "",
                actorAvatar = source.actorResponse?.avatarUrl,
                createdDate = dateConverter.stringToDate(source.createdAt) ?: Date()
        )
    }
}