package com.example.tmdbapp.view.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.base.BaseVM
import com.example.tmdbapp.data.Resource
import com.example.tmdbapp.data.Status
import com.example.tmdbapp.model.Movie
import com.example.tmdbapp.model.Result
import com.example.tmdbapp.model.ResultX
import com.example.tmdbapp.repository.MovieRepository
import com.github.ajalt.timberkt.e
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@FlowPreview
@HiltViewModel
@ExperimentalCoroutinesApi
class MovieVM @Inject constructor(
    private val  moviesRepository: MovieRepository,
) : BaseVM(){

    val apiMovie: MutableLiveData<Resource<Movie>> = MutableLiveData()
    val movieDetail: MutableLiveData<Resource<Result>> = MutableLiveData()


    fun getMoviesList(): LiveData<Resource<Movie>> {
        return if (apiMovie.value?.data == null) {
            viewModelScope.launch {
                moviesRepository.getMovie().collect {
                    if (it.status == Status.SUCCESS) {
                        e { "denememe" }
                        apiMovie.postValue(it)
                    } else if (it.status == Status.ERROR) {
                        e { "patladı ${it.throwable}" }
                    }

                }
            }
            apiMovie
        } else {
            apiMovie
        }
    }

    fun getMoviewDetail(movieId : String): LiveData<Resource<Result>> {
        return if (movieDetail.value?.data == null) {
            viewModelScope.launch {
                moviesRepository.getDetail(movieId).collect {
                    if (it.status == Status.SUCCESS) {
                        movieDetail.postValue(Resource.success(it.data!!.body()))
                        e { "detaildeneme" }
                    } else if (it.status == Status.ERROR) {
                        e { "patladıdetail ${it.throwable}" }
                    }

                }
            }
            movieDetail
        } else {
            movieDetail
        }
    }


}