package com.example.tmdbapp.view.movie

import android.os.Bundle
import android.view.View
import androidx.navigation.navGraphViewModels
import com.example.tmdbapp.R
import com.example.tmdbapp.base.BaseFragment
import com.example.tmdbapp.databinding.FragmentMoviesExoplayerBinding
import com.example.tmdbapp.exo.Constants
import com.example.tmdbapp.exo.ExoUtil
import com.example.tmdbapp.extension.lifecycleAwareHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@AndroidEntryPoint
class MovieExoPlayerFragment : BaseFragment<FragmentMoviesExoplayerBinding>(R.layout.fragment_movies_exoplayer) {
    @Inject
    lateinit var exoUtil: ExoUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exoUtil.setPlayerView(binding.exoPlayerView)
        exoUtil.setUrl(Constants.EXO_PLAYER_PROGRESSIVE_SAMPLE_URL)

        lifecycleAwareHandler(this, exoUtil)

    }

}