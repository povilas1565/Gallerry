package com.example.gallerry.utils
import com.bumptech.glide.Glide
import android.widget.ImageView
import com.example.gallerry.R
import java.io.File

fun ImageView.loadImage(file: File) {
    Glide.with(this)
        .load(file)
        .apply(RequestOptions()
            .placeholder(R.drawable.shadow_gradient)
            .centerCrop())
        .into(this)
}
