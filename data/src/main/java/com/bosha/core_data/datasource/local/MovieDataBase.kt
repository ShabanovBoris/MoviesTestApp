package com.bosha.core_data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bosha.core_data.dto.local.ActorEntity
import com.bosha.core_data.dto.local.FavoriteMovieEntity
import com.bosha.core_data.dto.local.MovieDetailsEntity
import com.bosha.core_data.dto.local.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class, ActorEntity::class, FavoriteMovieEntity::class],
    version = 1
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}