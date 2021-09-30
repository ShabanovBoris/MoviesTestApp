package com.bosha.data.dto.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMainResponse(
//    @SerialName("dates")
//    val dates: Dates? = null,
//    @SerialName("page")
//    val page: Int? = null,
//    @SerialName("total_pages")
//    val totalPages: Int,
    @SerialName("results")
    val results: List<JsonMovie>,
//    @SerialName("total_results")
//    val totalResults: Int? = null
)


//@Serializable
//data class Dates(
//    @SerialName("maximum")
//    val maximum: String? = null,
//    @SerialName("minimum")
//    val minimum: String? = null
//)