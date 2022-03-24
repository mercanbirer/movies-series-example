package com.example.tmdbapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TV(
    @SerialName("adult")
    val page: Int,
    @SerialName("results")
    val results: List<ResultX>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)