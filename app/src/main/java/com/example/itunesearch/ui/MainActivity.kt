package com.example.itunesearch.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itunesearch.BuildConfig
import com.example.itunesearch.R
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.data.model.Track
import com.example.itunesearch.databinding.ActivityMainBinding
import com.example.itunesearch.util.GridItemDecorator
import com.example.itunesearch.util.Utils
import com.example.itunesearch.util.ViewModelProviderFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Lamda Ref : https://marcin-chwedczuk.github.io/lambda-expressions-in-kotlin
 * */
class MainActivity : AppCompatActivity() {

    private var currentMediaUrl: String? = null
    private lateinit var dataSourceFactory: DefaultHttpDataSourceFactory
    private var playbackPosition: Long = 0
    private var stoppedByUser: Boolean = false
    private lateinit var player: SimpleExoPlayer
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TrackAdapter
    private lateinit var listData: List<Track>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val factory = ViewModelProviderFactory(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        rv_tracks.layoutManager = GridLayoutManager(this, 2)
        rv_tracks.addItemDecoration(GridItemDecorator(2, 50, true))
        (rv_tracks.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        adapter = TrackAdapter(clickListener)
        adapter.setHasStableIds(true)
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
            et_search.requestFocus()
            Utils.showKeyboard(this)
        }
    }

    private fun fetchQuery(artist: String) {
        viewModel.fetch(artist, compositeDisposable)
        viewModel.getLiveDataTracksByArtist(artist)?.observe(this, Observer {
            adapter.setList(it)
            listData = it
            Log.i(TAG, "onCreate: ${it.size}")
        })
    }

    private fun initExoPlayer() {
        dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer-" + BuildConfig.APPLICATION_ID)
        player = ExoPlayerFactory.newSimpleInstance(this);
        player.playWhenReady = true
        if (currentMediaUrl != null && !stoppedByUser) {
            player.prepare(getMediaSource())
            player.seekTo(playbackPosition)
        }
    }

    private fun getMediaSource(): MediaSource? {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(currentMediaUrl))
    }

    private fun releasePlayer() {
        playbackPosition = player.currentPosition
        player.release()
    }

    override fun onStart() {
        super.onStart()
        initExoPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private var previousPosition: Int = -1

    private val clickListener: (Int, String?) -> Unit = { position, url ->

        if (Utils.isNetworkAvailable(this)) {

            if (position != previousPosition) {
                currentMediaUrl = url
                player.prepare(getMediaSource())
                player.seekTo(0)

                //update adapter
                if (previousPosition != -1) {
                    listData[previousPosition].isPlaying = false
                    adapter.notifyItemChanged(previousPosition)
                }
                previousPosition = position

            } else if (position == previousPosition && !player.isPlaying) {
                player.prepare(getMediaSource())
                player.seekTo(0)

            } else {

                player.stop()
                stoppedByUser = true
            }

        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
