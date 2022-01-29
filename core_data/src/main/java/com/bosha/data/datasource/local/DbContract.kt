package com.bosha.data.datasource.local

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "movie.db"

    object Movie{
        const val TABLE_NAME = "movie"
        const val COLUMN_ID = BaseColumns._ID
        const val TITLE = "movie_title"
        const val GENRES = "genres"
        const val RATING = "rating"
        const val IMAGE_URL = "image_url"
        const val RELEASE_DATE = "release_date"
        const val POPULARITY = "popularity"
        const val IS_LIKED = "is_liked"
    }

    object FavoriteMovie{
        const val TABLE_NAME = "favorite_movie"
    }

    object MovieDetails{
        const val TABLE_NAME = "movie_details"
        const val COLUMN_ID = BaseColumns._ID
        const val TITLE = "title"
        const val STORY = "overview"
        const val RUNTIME = "runtime"
        const val IMAGE = "imageBackdrop"
        const val GENRES = "genres"
        const val ACTORS = "actors"
        const val VOTES = "votes"
    }
}