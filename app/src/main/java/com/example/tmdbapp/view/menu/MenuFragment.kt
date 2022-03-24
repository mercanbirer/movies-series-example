package com.example.tmdbapp.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.BundleCompat
import androidx.core.os.bundleOf
import androidx.navigation.navGraphViewModels
import com.example.tmdbapp.R
import com.example.tmdbapp.base.BaseFragment
import com.example.tmdbapp.bundle.BundleKeys
import com.example.tmdbapp.data.Status
import com.example.tmdbapp.databinding.FragmentMenuBinding
import com.example.tmdbapp.extension.navigateSafe
import com.example.tmdbapp.extension.show
import com.example.tmdbapp.view.serie.SerieVM
import com.example.tmdbapp.view.movie.MovieVM
import com.example.tmdbapp.view.movie.adapter.MoviesAdapter
import com.example.tmdbapp.view.serie.adapter.SeriesAdapter
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var seriesAdapter: SeriesAdapter

    private val movieVM: MovieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }
    private val serieVM: SerieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieList()
        binding.btnMovies.setOnClickListener {
            movieList()
        }
        binding.btnTv.setOnClickListener {
            tvList()
        }
        binding.btnImageShare.setOnClickListener {
            share()
        }

        binding.moviesGridView.setOnItemClickListener { parent, view, position, id ->
            val item = moviesAdapter.getItem(position)
            val args = bundleOf(
                BundleKeys.MOVIE_NAME to  item.title,
                BundleKeys.MOVIE_DESC to item.overview,
                BundleKeys.MOVIE_IMG to item.poster_path
            )
            navigateSafe(R.id.action_menuFragment_to_movieInfoFragment, args)
        }
    }

    private fun tvList() {
        serieVM.apiTv.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    seriesAdapter = SeriesAdapter(requireContext(), it.data!!.results)
                    binding.moviesGridView.adapter = seriesAdapter
                    binding.moviesGridView.requestFocus()
                    binding.moviesGridView.show()
                    seriesAdapter.notifyDataSetChanged()
                    binding.btnTv.background =
                        requireContext().getDrawable(R.drawable.selector_button)
                    binding.btnMovies.background =
                        requireContext().getDrawable(R.color.transparent)
                }
                Status.ERROR -> e { "error" }
                Status.LOADING -> i { "Search Loading" }
            }
        }
        serieVM.getTVList()
    }

    private fun movieList() {
        movieVM.apiMovie.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    moviesAdapter = MoviesAdapter(requireContext(), it.data!!.results)
                    binding.moviesGridView.adapter = moviesAdapter
                    binding.moviesGridView.requestFocus()
                    binding.moviesGridView.show()
                    moviesAdapter.notifyDataSetChanged()
                    binding.btnMovies.background =
                        requireContext().getDrawable(R.drawable.selector_button)
                    binding.btnTv.background =
                        requireContext().getDrawable(R.color.transparent)

                }
                Status.ERROR -> e { "error" }
                Status.LOADING -> i { "Search Loading" }
            }
        }
        movieVM.getMoviesList()
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Uygulama Linki")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}

