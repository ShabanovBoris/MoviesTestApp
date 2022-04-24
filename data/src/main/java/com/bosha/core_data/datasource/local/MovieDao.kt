package com.bosha.core_data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bosha.core_data.dto.local.FavoriteMovieEntity
import com.bosha.core_data.dto.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    /** Default getter */
    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} ORDER BY ${DbContract.Movie.POPULARITY} DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    /** Paging getter */
    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} ORDER BY ${DbContract.Movie.POPULARITY} DESC")
    fun getMoviesPaging(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>)

    @Query("DELETE FROM ${DbContract.Movie.TABLE_NAME}")
    suspend fun clear()

    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} WHERE ${DbContract.Movie.COLUMN_ID} = :id")
    suspend fun getById(id: String): MovieEntity

    @Query("SELECT * FROM ${DbContract.Movie.TABLE_NAME} WHERE ${DbContract.Movie.TITLE} LIKE '%' || :title || '%'")
    fun getByTitle(title: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM ${DbContract.FavoriteMovie.TABLE_NAME} ORDER BY ${DbContract.Movie.POPULARITY} DESC")
    fun getFavoritesMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("DELETE FROM ${DbContract.FavoriteMovie.TABLE_NAME} WHERE ${DbContract.Movie.COLUMN_ID} = :id")
    suspend fun deleteFromFavorite(id: String)
}