package com.example.itunesearch.di.module

import android.content.Context
import com.example.itunesearch.data.Repository
import com.example.itunesearch.data.db.TrackDatabase
import com.example.itunesearch.data.rest.RetrofitApi
import com.example.itunesearch.di.ApplicationContext
import com.example.itunesearch.di.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Module(includes = [ViewModelModule::class, OkHttpClientModule::class])
class ApplicationModule {

    @Provides
    fun retrofitApi(retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

    @ApplicationScope
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @ApplicationScope
    @Provides
    fun getRepository(retrofitApi: RetrofitApi, database: TrackDatabase?): Repository? {
        return Repository(database, retrofitApi)
    }

    @ApplicationScope
    @Provides
    fun getDatabase(@ApplicationContext context: Context): TrackDatabase? {
        return TrackDatabase.getDataBase(context)
    }

    companion object {
        const val BASE_URL: String = "https://itunes.apple.com"
    }
}