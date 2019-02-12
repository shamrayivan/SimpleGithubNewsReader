package com.lab.esh1n.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by esh1n on 3/7/18.
 */

class UserResponse {
    @SerializedName("login")
    val login: String? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("bio")
    val biography: String? = null
    @SerializedName("avatar_url")
    val avatar: String? = null
    @SerializedName("location")
    val location: String? = null
    @SerializedName("email")
    val email: String? = null
}
