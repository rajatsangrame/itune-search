package com.example.itunesearch.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.itunesearch.R


/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
object BindingUtil {

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImage(imageView: ImageView, url: String) {
        val context = imageView.context
        Glide.with(imageView)
            .load(url)
            .placeholder(context.resources.getDrawable(R.color.cardBackground))
            .transform(CenterCrop(), RoundedCorners(8))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("togglePlay")
    fun loadImage(imageView: ImageView, isPlaying: Boolean) {
        val context = imageView.context
        if (isPlaying) {
            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_stop))
        } else {
            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_play))
        }
    }
}