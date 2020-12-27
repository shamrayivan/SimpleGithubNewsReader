package com.lab.esh1n.github.events.mapper

import com.shamray.lab.cache.entity.PhotoEntity
import com.lab.esh1n.github.domain.base.Mapper
import com.lab.esh1n.github.events.EventModel


class EventModelMapper : Mapper<PhotoEntity, EventModel>() {

    override fun map(source: PhotoEntity): EventModel {
        return EventModel(
                id = source.id,
                actorAvatar = source.actorAvatar ?: "",
                isLiked = source.like
        )
    }
}