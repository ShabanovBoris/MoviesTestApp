package com.bosha.core_domain.entities


data class Movie(
    val id: Int,
    val pgAge: Int? = null,
    val title: String,
    val genres: List<Genre>,
    val reviewCount: Int? = null,
    val isLiked: Boolean = false,
    val rating: Double,
    val imageUrl: String,
    val detailImageUrl: String? = null,
    val storyLine: String? = null,
    val releaseDate:String,
    val popularity:Double,
    val page: Int? = 0
)