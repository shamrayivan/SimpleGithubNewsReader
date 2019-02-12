package com.lab.esh1n.data.api

import com.lab.esh1n.data.api.response.RepositoryResponse
import com.lab.esh1n.data.api.response.SearchResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by esh1n on 3/7/18.
 */

interface APIService {

    @GET("/search/repositories")
    fun searchRepositories(@Query("q") query: String,
                           @Query("page") page: Int): Observable<Response<SearchResponse<RepositoryResponse>>>

}