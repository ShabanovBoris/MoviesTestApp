package com.bosha.core_domain.entities

data class MovieDetails(
    val id:Int,
    val title:String,
    val overview:String,
    val runtime:Int,
    val imageBackdrop:String,
    val genres:List<Genre>,
    val actors:List<Actor>,
    val votes:Double
)