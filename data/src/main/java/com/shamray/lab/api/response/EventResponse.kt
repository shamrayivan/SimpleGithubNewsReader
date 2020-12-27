package com.shamray.lab.api.response

import com.google.gson.annotations.SerializedName

data class EventResponse(

        @field:SerializedName("actor")
        val actorResponse: ActorResponse? = null,

        @field:SerializedName("repo")
        val repositoryResponse: RepositoryResponse? = null,

        @field:SerializedName("created_at")
        val createdAt: String,

        @field:SerializedName("id")
        val id: Long,

        @field:SerializedName("type")
        val type: String? = null
)