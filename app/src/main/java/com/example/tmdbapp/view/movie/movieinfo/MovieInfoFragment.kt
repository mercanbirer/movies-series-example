package com.example.tmdbapp.view.movie.movieinfo

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.tmdbapp.R
import com.example.tmdbapp.bundle.BundleKeys
import com.example.tmdbapp.data.Status
import com.example.tmdbapp.databinding.FragmentMovieInfoBinding
import com.example.tmdbapp.extension.navigateSafe
import com.example.tmdbapp.model.Result
import com.example.tmdbapp.modules.ApplicationModule
import com.example.tmdbapp.view.movie.MovieVM
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

import kotlinx.serialization.ExperimentalSerializationApi
import java.lang.Exception
import java.util.ArrayList

class MovieInfoFragment : DialogFragment() {

    private val movieVM: MovieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }
    private var movieId: Int = -1
    lateinit var movieItem: Result
    private lateinit var binding: FragmentMovieInfoBinding

    @ExperimentalSerializationApi
    val appModule = ApplicationModule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.VodTheme)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_info, container, false)
        binding = FragmentMovieInfoBinding.bind(view)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            binding.playButton.requestFocus()
        }
        binding.description.movementMethod = ScrollingMovementMethod()

        val name = requireArguments().getSerializable(BundleKeys.MOVIE_NAME)
        binding.name.text = name.toString()
        val description = requireArguments().getSerializable(BundleKeys.MOVIE_DESC)
        binding.description.text = description.toString()

        val img = requireArguments().getSerializable(BundleKeys.MOVIE_IMG)
        Glide.with(this).load(appModule.BASE_IMAGE_URL_API + img.toString())
            .into(binding.poster)

        movieVM.getMoviewDetail(movieId.toString())

        binding.playButton.setOnClickListener {
            navigateSafe(R.id.action_movieInfoFragment_to_movieExoPlayerFragment)
        }
    }
}