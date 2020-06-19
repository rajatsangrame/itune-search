package com.example.itunesearch.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.itunesearch.data.Repository
import com.example.itunesearch.data.model.Track
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */

/* Todo: Replace @ViewModel with @AndroidViewModel */
class MainViewModel(context: Context) : ViewModel() {

    private var repository: Repository = Repository(context)
    private val liveData: MutableLiveData<List<Track>> = MutableLiveData()

    fun getLiveDataTracksByArtist(): LiveData<List<Track>>? {
        return liveData
    }

    fun fetch(query: String, compositeDisposable: CompositeDisposable, owner: LifecycleOwner) {
        repository.query(query, compositeDisposable)
        repository.getLiveDataTracksByArtist(query, liveData)
    }
}