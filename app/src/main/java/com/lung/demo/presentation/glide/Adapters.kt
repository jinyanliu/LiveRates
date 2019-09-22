package com.lung.demo.presentation.glide

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("img")
fun ImageView.loadImage(img: String?) {
    GlideApp .with(this)
        .load(img)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}