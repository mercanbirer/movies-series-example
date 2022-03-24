package com.example.tmdbapp.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdbapp.R
import com.example.tmdbapp.modules.ApplicationModule
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit


@ExperimentalSerializationApi
@BindingAdapter("moviePoster")
fun setMoviePoster(imageView: AppCompatImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .timeout(15000)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_launcher_background)
                .fitCenter()
        )
        .fitCenter()
        .into(imageView)
}


