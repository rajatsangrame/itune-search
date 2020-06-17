package com.example.itunesearch.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.itunesearch.data.Repository
import com.example.itunesearch.data.model.Track
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */
class MainViewModel(context: Context) : ViewModel() {

    private var repository: Repository = Repository(context)

    fun getLiveData(): LiveData<List<Track>>? {
        return repository.getAllTracks()
    }

    fun fetch(query: String, compositeDisposable: CompositeDisposable) {
        repository.query(query, compositeDisposable)
    }
}