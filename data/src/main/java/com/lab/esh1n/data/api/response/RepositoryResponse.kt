package com.lab.esh1n.data.api.response

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("url")
        val url: String? = null
)