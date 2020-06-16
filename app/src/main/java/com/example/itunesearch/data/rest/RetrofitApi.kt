package com.example.itunesearch.data.rest

import com.example.itunesearch.data.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
interface RetrofitApi {

    @GET("/search")
    fun searchQuerySingle(@Query("term") query: String): Single<ApiResponse>
}