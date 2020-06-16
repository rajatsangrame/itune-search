package com.example.itunesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesearch.R
import com.example.itunesearch.data.model.Track
import com.example.itunesearch.databinding.TrackItemBinding

/**
 * Created by Rajat Sangrame on 17/6/20.
 * http://github.com/rajatsangrame
 */
class TrackAdapter : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    private var trackList: List<Track> = ArrayList()

    fun setList(trackList: List<Track>) {
        this.trackList = trackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TrackItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.track_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackAdapter.ViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    inner class ViewHolder(var binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track?) {
            binding.track = track
            binding.executePendingBindings()
        }
    }
}
