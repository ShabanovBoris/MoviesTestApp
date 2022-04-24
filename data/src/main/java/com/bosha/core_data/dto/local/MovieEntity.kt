package com.bosha.core_data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bosha.core_data.datasource.local.DbContract
import com.bosha.core_data.datasource.local.DbContract.Movie.IS_LIKED
import com.bosha.core_data.datasource.local.DbContract.Movie.POPULARITY


@Entity(
    tableName = DbContract.Movie.TABLE_NAME,
    indices = [Index(DbContract.Movie.COLUMN_ID)]
)
data class MovieEntity(

    @PrimaryKey
    @ColumnInfo(name = DbContract.Movie.COLUMN_ID)
    val id: Int = 0,

    @ColumnInfo(name = DbContract.Movie.TITLE)
    val title: String,

    @ColumnInfo(name = DbContract.Movie.GENRES)
    val genres: String,

    @ColumnInfo(name = DbContract.Movie.RATING)
    val rating: Double,

    @ColumnInfo(name = DbContract.Movie.IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = DbContract.Movie.RELEASE_DATE)
    val releaseDate: String,

    @ColumnInfo(name = POPULARITY)
    val popularity: Double,

    @ColumnInfo(name = IS_LIKED)
    val isLiked: Boolean,

    val page: Int?
)