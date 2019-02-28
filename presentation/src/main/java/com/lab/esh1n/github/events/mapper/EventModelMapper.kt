package com.lab.esh1n.github.events.mapper

import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.domain.base.Mapper
import com.lab.esh1n.github.domain.events.mapper.ISO8061DateMapper
import com.lab.esh1n.github.events.EventModel


class EventModelMapper : Mapper<EventEntity, EventModel>() {

    private val dateMapper = ISO8061DateMapper()
    override fun map(source: EventEntity): EventModel {
        return EventModel(
                id = source.id,
                type = source.type,
                repositoryName = extractRepoName(source.repositoryName),
                actorName = source.actorName,
                actorAvatar = source.actorAvatar ?: "",
                repositoryLink = prepareRepositoryLink(source.repositoryName),
                createdDate = dateMapper.mapInverse(source.createdDate)
        )
    }

    private fun extractRepoName(repoPath: String): String {
        return repoPath.substring(repoPath.lastIndexOf('/') + 1)
    }

    private fun prepareRepositoryLink(repositoryName: String): String {
        return "$GITBUB_URL$repositoryName"
    }

    companion object {
        const val GITBUB_URL = "https://github.com/"
    }
}