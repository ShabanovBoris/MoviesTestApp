package com.bosha.core_data.di

import android.content.Context
import androidx.room.Room
import com.bosha.core_data.datasource.local.DbContract
import com.bosha.core_data.datasource.local.MovieDao
import com.bosha.core_data.datasource.local.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideMovieDataBase(@ApplicationContext appContext: Context): MovieDataBase =
        Room.databaseBuilder(
            appContext,
            MovieDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(movieDataBase: MovieDataBase): MovieDao =
        movieDataBase.movieDao()
}