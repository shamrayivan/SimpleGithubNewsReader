package com.shamray.lab.api.response

import com.google.gson.annotations.SerializedName

data class ActorResponse(

        @field:SerializedName("display_login")
        val displayLogin: String? = null,

        @field:SerializedName("avatar_url")
        val avatarUrl: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("login")
        val login: String? = null,

        @field:SerializedName("gravatar_id")
        val gravatarId: String? = null,

        @field:SerializedName("url")
        val url: String? = null
)