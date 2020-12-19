package com.example.itunesearch.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itunesearch.App
import com.example.itunesearch.R
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.data.model.Track
import com.example.itunesearch.data.rest.ApiCallback
import com.example.itunesearch.databinding.FragmentHomeBinding
import com.example.itunesearch.di.component.DaggerHomeFragmentComponent
import com.example.itunesearch.di.component.DaggerMainActivityComponent
import com.example.itunesearch.di.component.HomeFragmentComponent
import com.example.itunesearch.di.component.MainActivityComponent
import com.example.itunesearch.di.module.HomeFragmentModule
import com.example.itunesearch.di.module.MainActivityModule
import com.example.itunesearch.ui.main.MainActivity
import com.example.itunesearch.util.GridItemDecorator
import com.example.itunesearch.util.SimpleIdlingResource
import com.example.itunesearch.util.Utils
import com.example.itunesearch.util.ViewModelFactory
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeFragment : Fragment() {


    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var dataSourceFactory: DefaultHttpDataSourceFactory

    @Inject
    lateinit var trackAdapter: TrackAdapter

    //region Global Params
    private lateinit var player: SimpleExoPlayer
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var listData: List<Track>
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var playbackPosition: Long = 0
    private var previousPosition: Int = -1
    private var currentMediaUrl: String? = null
    private var stoppedByUser: Boolean = false
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDependency()

        trackAdapter.setListener(clickListener)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewModel.getLiveDataTracksByArtist()?.observe(viewLifecycleOwner, Observer {
            Log.d(MainActivity.TAG, "onCreate: ${it.size}")
            trackAdapter.setList(it)
            listData = it
            if (it.isNotEmpty()) {
                showRecyclerView()
            }
        })

        //region UI Components
        binding.rvTracks.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridItemDecorator(2, 50, true))
            adapter = trackAdapter
        }
        binding.btnClear.setOnClickListener{
            clearText()
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString()
                if (query.isNotEmpty()) {
                    resetPlayerParams()
                    fetchQuery(query)
                    Utils.hideKeyboard(context!!, binding.etSearch)
                }
            }
            true
        }
    }

    private fun clearText() {
        binding.etSearch.text.clear()
        binding.etSearch.requestFocus()
        Utils.showKeyboard(context!!)
    }

    private fun showMessage(msg: String?) {
        binding.rvTracks.visibility = View.GONE
        binding.tvErrorMsg.visibility = View.VISIBLE
        binding.tvErrorMsg.text = msg
    }

    private fun showRecyclerView() {
        binding.rvTracks.visibility = View.VISIBLE
        binding.tvErrorMsg.visibility = View.GONE
    }

    private fun getDependency() {
        val component: HomeFragmentComponent = DaggerHomeFragmentComponent
            .builder().applicationComponent(App.get(context!!)?.getComponent())
            .homeFragmentModule(HomeFragmentModule(this))
            .build()
        component.injectFragment(this)
    }

    private fun fetchQuery(artist: String) {
        SimpleIdlingResource.increment()
        viewModel.fetch(artist, compositeDisposable, object : ApiCallback {
            override fun success() {
                SimpleIdlingResource.decrement()
            }

            override fun failure(msg: String?) {
                showMessage(msg)
                SimpleIdlingResource.decrement()
            }
        })
    }

    private fun initExoPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(context);
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
            trackAdapter.notifyItemChanged(previousPosition)
        }
    }

    private fun resetPlayerParams() {
        playbackPosition = 0
        previousPosition = -1
        currentMediaUrl = null
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }
}