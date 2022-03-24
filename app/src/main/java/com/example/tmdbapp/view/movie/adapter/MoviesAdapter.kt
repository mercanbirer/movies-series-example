package com.example.tmdbapp.view.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tmdbapp.base.BaseAdapter
import com.example.tmdbapp.databinding.ItemMoviesBinding
import com.example.tmdbapp.model.Result
import com.example.tmdbapp.modules.ApplicationModule
import kotlinx.serialization.ExperimentalSerializationApi

class MoviesAdapter(
    private val context: Context,
    private val data: List<Result>,
) : BaseAdapter<Result>(context, data) {

    @ExperimentalSerializationApi
    val appModule = ApplicationModule()

    @ExperimentalSerializationApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemMoviesBinding
        if (convertView == null) {
            binding = ItemMoviesBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemMoviesBinding
        }
        val movie = getItem(position)
        binding.movie = movie

        Glide.with(context).load(
            appModule.BASE_IMAGE_URL_API + data[position]
                .poster_path
        ).into(binding.itemMoviePoster)

        return binding.root
    }
}
