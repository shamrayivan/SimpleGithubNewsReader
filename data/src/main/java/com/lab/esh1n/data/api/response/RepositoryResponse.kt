package com.lab.esh1n.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by esh1n on 3/7/18.
 */

class RepositoryResponse {
    @SerializedName("id")
    val id: Long = 0
    @SerializedName("owner")
    val owner: UserResponse? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("language")
    val language: String? = null
    @SerializedName("watchers_count")
    val watchCount: Int = 0
    @SerializedName("stargazers_count")
    val starsCount: Int = 0
    @SerializedName("forks_count")
    val forksCount: Int = 0
}