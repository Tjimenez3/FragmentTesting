package com.vogella.android.fragmenttesting.constants

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vogella.android.fragmenttesting.R

fun ImageView?.loadImage(imageUrl: String?) {

    this?.run {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}
fun ImageView?.loadCircularImage(imageUrl: String?) {

    this?.run {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .circleCrop()
            .into(this)
    }
}