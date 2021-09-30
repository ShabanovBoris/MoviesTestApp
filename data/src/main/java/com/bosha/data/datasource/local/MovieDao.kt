package com.bosha.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} ORDER BY ${DbContract.Movie.POPULARITY} DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>)

    @Query("DELETE FROM ${DbContract.Movie.TABLE_NAME}")
    suspend fun clear()

    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} WHERE ${DbContract.Movie.COLUMN_ID} = :id")
    suspend fun getById(id: String): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM ${DbContract.FavoriteMovie.TABLE_NAME} ORDER BY ${DbContract.Movie.POPULARITY} DESC")
    fun getFavoritesMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("DELETE FROM ${DbContract.FavoriteMovie.TABLE_NAME} WHERE ${DbContract.Movie.COLUMN_ID} = :id")
    suspend fun deleteFromFavorite(id: String)
}