package com.example.itunesearch.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.itunesearch.data.db.TrackDatabase
import com.example.itunesearch.data.model.ApiResponse
import com.example.itunesearch.data.model.Track
import com.example.itunesearch.data.rest.RetrofitApi
import com.example.itunesearch.data.rest.RetrofitClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
class Repository(private val context: Context) {

    private val db: TrackDatabase? = TrackDatabase.getDataBase(context)
    private val api: RetrofitApi = RetrofitClient.getApi()
    private val ioExecutor: Executor = Executors.newSingleThreadExecutor()

    fun query(artist: String, compositeDisposable: CompositeDisposable) {

        val single: Single<ApiResponse> = api.searchQuerySingle(artist)
        compositeDisposable.add(
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    bulkInsert(it.results)
                    Log.d(TAG, "query: ${it.resultCount}")
                }, {
                    Log.d(TAG, "query: ${it.localizedMessage}")
                })
        )
    }

    private fun bulkInsert(list: List<Track>) {
        ioExecutor.execute {
            db?.trackDao()?.bulkInsert(list)
            Log.d(TAG, "bulkInsert: ${list.size}")
        }
    }

    fun getLiveDataTracksByArtist(artist: String): LiveData<List<Track>>? {
        return db?.trackDao()?.getAllTracks(artist)
    }

    companion object {
        private const val TAG = "Repository"
    }
}