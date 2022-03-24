package com.example.tmdbapp.repository

import com.example.tmdbapp.api.TMDBApi
import com.example.tmdbapp.data.Resource
import com.example.tmdbapp.model.Movie
import com.example.tmdbapp.model.Result
import com.example.tmdbapp.model.ResultX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val tmdbApi: TMDBApi,
) {

    fun getMovie(): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.loading(null))
            val movieList = tmdbApi.getMovie()
            emit(Resource.success(movieList))
        }.catch {
            emit(Resource.error(it.message!!, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getDetail(moviesId: String): Flow<Resource<Response<Result>>> {
        return flow {
            emit(Resource.loading(null))
            val tvDetail = tmdbApi.movieDetail(moviesId)
            emit(Resource.success(tvDetail))
        }.catch {
            emit(Resource.error(it.message!!, null, it))
        }.flowOn(Dispatchers.IO)
    }


}