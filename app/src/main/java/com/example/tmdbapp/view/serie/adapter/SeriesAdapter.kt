package com.example.tmdbapp.view.serie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tmdbapp.base.BaseAdapter
import com.example.tmdbapp.databinding.ItemTvBinding
import com.example.tmdbapp.model.ResultX
import com.example.tmdbapp.modules.ApplicationModule
import kotlinx.serialization.ExperimentalSerializationApi

class SeriesAdapter(
    private val context: Context,
    private val data: List<ResultX>,
) : BaseAdapter<ResultX>(context, data) {

    @ExperimentalSerializationApi
    val appModule = ApplicationModule()

    @ExperimentalSerializationApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemTvBinding
        if (convertView == null) {
            binding = ItemTvBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemTvBinding
        }
        val tv = getItem(position)
        binding.tv = tv

        Glide.with(context).load(
            appModule.BASE_IMAGE_URL_API + tv
                .poster_path
        ).into(binding.itemMoviePoster)

        return binding.root
    }
}
