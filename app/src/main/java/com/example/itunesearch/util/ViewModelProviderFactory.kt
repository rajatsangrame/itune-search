package com.example.itunesearch.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesearch.data.Repository
import com.example.itunesearch.ui.MainViewModel

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */
class ViewModelProviderFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}