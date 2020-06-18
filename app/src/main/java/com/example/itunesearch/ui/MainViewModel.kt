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

/* Todo: Replace @ViewModel with @AndroidViewModel */
class MainViewModel(context: Context) : ViewModel() {

    private var repository: Repository = Repository(context)

    fun getLiveDataTracksByArtist(artist: String): LiveData<List<Track>>? {
        return  repository.getLiveDataTracksByArtist(artist)
    }

    fun fetch(query: String, compositeDisposable: CompositeDisposable) {
        repository.query(query, compositeDisposable)
    }

}