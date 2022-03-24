package com.example.tmdbapp.api

import com.example.tmdbapp.model.Movie
import com.example.tmdbapp.model.Result
import com.example.tmdbapp.model.ResultX
import com.example.tmdbapp.model.TV
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApi {

    @GET("discover/movie")
    suspend fun getMovie() : Movie

    @GET("discover/tv")
    suspend fun getTV() : TV

    @GET("tv/{tv_id}")
    suspend fun serieDetail(
        @Path("tv_id") tvId: Long,
    ): Response<ResultX>

    @GET("movie/{movie_id}")
    suspend fun movieDetail(
        @Path("movie_id") movieId: String,
    ): Response<Result>

}