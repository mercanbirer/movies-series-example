package com.example.tmdbapp.repository

import com.example.tmdbapp.api.TMDBApi
import com.example.tmdbapp.data.Resource
import com.example.tmdbapp.model.ResultX
import com.example.tmdbapp.model.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject


class SerieRepository @Inject constructor(
    private val tmdbApi: TMDBApi,
) {

    fun getTV(): Flow<Resource<TV>> {
        return flow {
            emit(Resource.loading(null))
            val tvList = tmdbApi.getTV()
            emit(Resource.success(tvList))
        }.catch {
            emit(Resource.error(it.message!!, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getDetail(seriesId: Long): Flow<Resource<Response<ResultX>>> {
        return flow {
            emit(Resource.loading(null))
            val tvDetail = tmdbApi.serieDetail(seriesId)
            emit(Resource.success(tvDetail))
        }.catch {
            emit(Resource.error(it.message!!, null, it))
        }.flowOn(Dispatchers.IO)
    }


}