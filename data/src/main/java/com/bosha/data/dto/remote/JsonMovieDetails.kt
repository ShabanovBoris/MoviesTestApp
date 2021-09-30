package com.bosha.data.dto.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovieDetails (
    val id:Int,
    @SerialName("original_title")
    val title:String,
    val overview:String?,
    val runtime:Int?,
    @SerialName("backdrop_path")
    val backdropPath:String?,
    val genres:List<JsonGenre>,
    @SerialName("vote_average")
    val votes:Double
)