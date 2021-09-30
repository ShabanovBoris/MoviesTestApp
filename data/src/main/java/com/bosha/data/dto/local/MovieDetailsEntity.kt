package com.bosha.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bosha.data.datasource.local.DbContract

@Entity(tableName = DbContract.MovieDetails.TABLE_NAME)
data class MovieDetailsEntity(

    @PrimaryKey
    @ColumnInfo(name = DbContract.MovieDetails.COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = DbContract.MovieDetails.TITLE)
    val title: String,

    @ColumnInfo(name = DbContract.MovieDetails.STORY)
    val overview: String,

    @ColumnInfo(name = DbContract.MovieDetails.RUNTIME)
    val runtime: Int,

    @ColumnInfo(name = DbContract.MovieDetails.IMAGE)
    val imageBackdrop: String,

    @ColumnInfo(name = DbContract.MovieDetails.GENRES)
    val genres: String,

    @ColumnInfo(name = DbContract.MovieDetails.ACTORS)
    val actorsId: String,

    @ColumnInfo(name = DbContract.MovieDetails.VOTES)
    val votes: Double
)