package com.bosha.domain.entities

import com.bosha.domain.entities.Actor
import com.bosha.domain.entities.Genre
import java.io.Serializable

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