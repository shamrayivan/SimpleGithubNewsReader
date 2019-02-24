package com.lab.esh1n.data.api

import com.lab.esh1n.data.api.response.EventResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by esh1n on 3/7/18.
 */

interface APIService {

    @GET("/events")
    fun getEvents(): Single<List<EventResponse>>

}