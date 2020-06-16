package com.example.itunesearch.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.itunesearch.R
import com.example.itunesearch.data.model.ApiResponse
import com.example.itunesearch.data.rest.RetrofitClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val single: Single<ApiResponse> = RetrofitClient.getApi().searchQuerySingle("taylor")
        compositeDisposable.add(
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "onCreate: ${it.resultCount}")
                }, {
                    Log.d(TAG, "onCreate: ${it.localizedMessage}")
                })
        )
    }
}