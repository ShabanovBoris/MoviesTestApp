package com.bosha.data.di

import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.local.impl.MovieLocalDataSource
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource
import com.bosha.data.repositoryImpl.RepositoryImpl
import com.bosha.domain.interactors.AddMoviesInteractor
import com.bosha.domain.interactors.DeleteMoviesInteractor
import com.bosha.domain.interactors.GetMoviesInteractor
import com.bosha.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): MovieRepository

    @Binds
    fun bindRemoteDataSource(remoteDataSource: MoviesRemoteDataSource): RemoteDataSource

    @Binds
    fun bindLocalDataSource(localDataSource: MovieLocalDataSource): LocalDataSource

    companion object{
        @Provides
        fun provideGetMoviesInteractor(repository: MovieRepository) =
            GetMoviesInteractor(repository)

        @Provides
        fun provideAddMoviesInteractor(repository: MovieRepository) =
            AddMoviesInteractor(repository)

        @Provides
        fun provideDeleteMoviesInteractor(repository: MovieRepository) =
            DeleteMoviesInteractor(repository)
    }
}