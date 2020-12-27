package com.shamray.lab.api

import com.shamray.lab.api.response.EventResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by esh1n on 3/7/18.
 */

interface APIService {

    @GET("/users/{username}/received_events")
    fun getEvents(@Path("username") userName: String, @Query("page") page: Int): Single<List<EventResponse>>

}