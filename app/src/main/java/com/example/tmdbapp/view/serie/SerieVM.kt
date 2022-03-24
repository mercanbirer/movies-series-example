package com.example.tmdbapp.view.serie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.base.BaseVM
import com.example.tmdbapp.data.Resource
import com.example.tmdbapp.data.Status
import com.example.tmdbapp.model.ResultX
import com.example.tmdbapp.model.TV
import com.example.tmdbapp.repository.SerieRepository
import com.github.ajalt.timberkt.e
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SerieVM @Inject constructor(
    private val serieRepository: SerieRepository,
) : BaseVM() {

    val apiTv: MutableLiveData<Resource<TV>> = MutableLiveData()
    val serieDetail: MutableLiveData<Resource<Response<ResultX>>> = MutableLiveData()

    fun getTVList(): LiveData<Resource<TV>> {
        return if (apiTv.value?.data == null) {
            viewModelScope.launch {
                serieRepository.getTV().collect {
                    if (it.status == Status.SUCCESS) {
                        e { "denememe" }
                        apiTv.postValue(it)
                    } else if (it.status == Status.ERROR) {
                        e { "patladı ${it.throwable}" }
                    }

                }
            }
            apiTv
        } else {
            apiTv
        }
    }

    fun getSerieDetail(serieId: Long): MutableLiveData<Resource<Response<ResultX>>> {
        return if (serieDetail.value?.data == null) {
            viewModelScope.launch {
                serieRepository.getDetail(serieId).collect {
                    if (it.status == Status.SUCCESS) {
                        e { "denememe" }
                        serieDetail.postValue(it)
                    } else if (it.status == Status.ERROR) {
                        e { "patladı ${it.throwable}" }
                    }

                }
            }
            serieDetail
        } else {
            serieDetail
        }
    }
}