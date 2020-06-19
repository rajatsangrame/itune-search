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
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Lamda Ref : https://marcin-chwedczuk.github.io/lambda-expressions-in-kotlin
 * */
class MainActivity : AppCompatActivity() {

    private lateinit var dataSourceFactory: DefaultHttpDataSourceFactory
    private var playbackPosition: Long = 0
    private var previousPosition: Int = -1
    private var currentMediaUrl: String? = null
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
        viewModel.getLiveDataTracksByArtist()?.observe(this, Observer {
            adapter.setList(it)
            listData = it
            Log.d(TAG, "onCreate: ${it.size}")
        })
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
        viewModel.fetch(artist, compositeDisposable, this)
    }

    private fun initExoPlayer() {
        dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer-" + BuildConfig.APPLICATION_ID)
        player = ExoPlayerFactory.newSimpleInstance(this);
        player.addListener(eventListener)
        player.playWhenReady = true
    }

    private fun getMediaSource(mediaUrl: String?): MediaSource? {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(mediaUrl))
    }

    private fun pausePlayer() {
        player.playWhenReady = false
    }

    private fun resumePlayer() {

        if (currentMediaUrl != null && !stoppedByUser) {
            player.prepare(getMediaSource(currentMediaUrl))
            player.seekTo(playbackPosition)
            updateAdapterPosition(true)

        }
    }

    private fun playMedia(mediaUrl: String?) {
        player.prepare(getMediaSource(mediaUrl))
        player.seekTo(0)
    }

    private fun releasePlayer() {
        playbackPosition = player.currentPosition
        player.release()
    }

    fun updateAdapterPosition(isPlaying: Boolean) {
        if (previousPosition != -1) {
            listData[previousPosition].isPlaying = isPlaying
            adapter.notifyItemChanged(previousPosition)
        }
    }

    private val clickListener: (Int, String?) -> Unit = { position, url ->

        if (position != previousPosition) {
            currentMediaUrl = url
            playMedia(url)
            //update adapter
            updateAdapterPosition(false)
            previousPosition = position

        } else if (position == previousPosition && !player.isPlaying) {
            playMedia(url)

        } else {

            player.stop()
            stoppedByUser = true
        }

    }

    private val eventListener: Player.EventListener = object : Player.EventListener {
        override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {}
        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onIsPlayingChanged(isPlaying: Boolean) {}
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onPlayerError(error: ExoPlaybackException) {
            updateAdapterPosition(false)
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                updateAdapterPosition(false)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initExoPlayer()
        resumePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
