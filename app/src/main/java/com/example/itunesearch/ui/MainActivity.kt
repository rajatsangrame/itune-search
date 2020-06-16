package com.example.itunesearch.ui

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesearch.R
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.data.model.ApiResponse
import com.example.itunesearch.data.rest.RetrofitClient
import com.example.itunesearch.databinding.ActivityMainBinding
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvTracks.layoutManager = LinearLayoutManager(this)
        val adapter = TrackAdapter()
        binding.rvTracks.adapter = adapter

        val single: Single<ApiResponse> = RetrofitClient.getApi().searchQuerySingle("taylor")
        compositeDisposable.add(
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.setList(it.results)
                    Log.d(TAG, "onCreate: ${it.resultCount}")
                }, {
                    Log.d(TAG, "onCreate: ${it.localizedMessage}")
                })
        )
    }
}