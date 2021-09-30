package com.bosha.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bosha.data.dto.local.ActorEntity
import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.MovieDetailsEntity
import com.bosha.data.dto.local.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class, ActorEntity::class, FavoriteMovieEntity::class],
    version = 1
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}