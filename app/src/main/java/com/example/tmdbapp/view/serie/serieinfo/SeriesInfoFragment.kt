package com.example.tmdbapp.view.serie.serieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.example.tmdbapp.R
import com.example.tmdbapp.databinding.FragmentSeriesInfoBinding
import com.example.tmdbapp.modules.ApplicationModule
import com.example.tmdbapp.view.serie.SerieVM
import com.github.ajalt.timberkt.e

import kotlinx.serialization.ExperimentalSerializationApi

class SeriesInfoFragment : DialogFragment() {

    private val serieVM: SerieVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }
    var serieId: Long = 0

    private lateinit var binding: FragmentSeriesInfoBinding

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
        val view = inflater.inflate(R.layout.fragment_series_info, container, false)
        binding = FragmentSeriesInfoBinding.bind(view)
        return binding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serieVM.serieDetail.observe(viewLifecycleOwner, {
            e { "girdi" }
        })
        serieVM.getSerieDetail(serieId)

    }
}