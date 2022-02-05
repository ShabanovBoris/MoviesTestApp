package com.bosha.core_data.dto.remote

import kotlinx.serialization.Serializable

@Serializable
class JsonGenre(val id: Int, val name: String)


@Serializable
data class JsonGenres(
    val genres: List<JsonGenre>
)