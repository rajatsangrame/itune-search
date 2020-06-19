package com.example.itunesearch.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.itunesearch.data.Repository
import com.example.itunesearch.data.model.Track
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: Repository
    private val queryLiveData = MutableLiveData<String>()
    private val liveData: LiveData<List<Track>> = Transformations.switchMap(queryLiveData) {
        repository.getLiveDataTracksByArtist(it)
    }

    fun getLiveDataTracksByArtist(): LiveData<List<Track>>? {
        return liveData
    }

    fun fetch(query: String, compositeDisposable: CompositeDisposable, owner: LifecycleOwner) {
        queryLiveData.postValue(query)
        repository.query(query, compositeDisposable)
    }
}