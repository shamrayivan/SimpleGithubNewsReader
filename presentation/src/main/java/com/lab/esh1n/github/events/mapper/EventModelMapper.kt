package com.lab.esh1n.github.events.mapper

import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.base.Mapper
import com.lab.esh1n.github.events.EventModel


class EventModelMapper : Mapper<EventEntity, EventModel>() {

    override fun map(source: EventEntity): EventModel {
        return EventModel(
                id = source.id,
                actorAvatar = source.actorAvatar ?: "",
                isLiked = source.like
        )
    }
}