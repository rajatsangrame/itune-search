package com.example.itunesearch.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itunesearch.R
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.data.model.ApiResponse
import com.example.itunesearch.data.rest.RetrofitClient
import com.example.itunesearch.databinding.ActivityMainBinding
import com.example.itunesearch.util.GridItemDecorator
import com.example.itunesearch.util.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val adapter = TrackAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvTracks.layoutManager = GridLayoutManager(this, 2)
        binding.rvTracks.addItemDecoration(GridItemDecorator(2, 50, true))
        binding.rvTracks.adapter = adapter
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString()
                if (query.isNotEmpty()) {
                    fetchQuery(query)
                    Utils.hideKeyboard(this)
                }
            }
            true
        }
        binding.btnClear.setOnClickListener {
            et_search.text.clear()
        }
    }

    private fun fetchQuery(query: String) {
        val single: Single<ApiResponse> = RetrofitClient.getApi().searchQuerySingle(query)
        compositeDisposable.add(
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.setList(it.results)
                    Log.d(TAG, "onCreate: ${it.resultCount}")
                }, {
                    Log.d(TAG, "onCreate: ${it.localizedMessage}")
                })
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}