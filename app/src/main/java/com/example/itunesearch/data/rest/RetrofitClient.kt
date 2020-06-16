package com.example.itunesearch.data.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
object RetrofitClient {

    private val client = OkHttpClient.Builder().build()
    private const val BASE_URL: String = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    fun getApi(): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }
}