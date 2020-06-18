package com.example.itunesearch.ui

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itunesearch.R
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.databinding.ActivityMainBinding
import com.example.itunesearch.util.GridItemDecorator
import com.example.itunesearch.util.Utils
import com.example.itunesearch.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val adapter = TrackAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val factory = ViewModelProviderFactory(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        rv_tracks.layoutManager = GridLayoutManager(this, 2)
        rv_tracks.addItemDecoration(GridItemDecorator(2, 50, true))
        rv_tracks.adapter = adapter
        et_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = et_search.text.toString()
                if (query.isNotEmpty()) {
                    fetchQuery(query)
                    Utils.hideKeyboard(this)
                }
            }
            true
        }
        btn_clear.setOnClickListener {
            et_search.text.clear()
        }
    }

    private fun fetchQuery(artist: String) {
        viewModel.fetch(artist, compositeDisposable)
        viewModel.getLiveDataTracksByArtist(artist)?.observe(this, Observer {
            adapter.setList(it)
            Log.i(TAG, "onCreate: ${it.size}")
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
