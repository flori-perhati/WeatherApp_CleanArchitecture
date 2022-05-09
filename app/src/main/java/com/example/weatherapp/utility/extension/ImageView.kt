package com.example.weatherapp.utility.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R

fun ImageView.loadImageFromPath(imageUrl: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_alert_outline)
        .error(R.drawable.ic_alert_outline)

    Glide.with(this.context).load(imageUrl).apply(options).into(this)
}