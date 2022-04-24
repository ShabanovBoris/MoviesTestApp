package com.bosha.core_data.dto.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMainResponse(
    @SerialName("results")
    val results: List<JsonMovie>
)

