package com.example.itunesearch.ui.home

import androidx.lifecycle.*
import com.example.itunesearch.data.Repository
import com.example.itunesearch.data.model.Track
import com.example.itunesearch.data.rest.ApiCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */

class HomeViewModel @Inject constructor(var repository: Repository?) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val liveData: LiveData<List<Track>> = Transformations.switchMap(queryLiveData) {
        repository?.getLiveDataTracksByArtist(it)
    }

    fun getLiveDataTracksByArtist(): LiveData<List<Track>>? {
        return liveData
    }

    fun fetch(query: String, compositeDisposable: CompositeDisposable, apiCallback: ApiCallback) {
        queryLiveData.postValue(query)
        repository?.query(query, compositeDisposable, apiCallback)
    }
}