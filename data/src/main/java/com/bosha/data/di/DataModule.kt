package com.bosha.data.di

import com.bosha.data.remote.RemoteDataSource
import com.bosha.data.remote.impl.MoviesRemoteDataSourceImpl
import com.bosha.data.repositoryImpl.RepositoryImpl
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
    fun bindRemoteDataSource(remoteDataSourceImpl: MoviesRemoteDataSourceImpl): RemoteDataSource

    companion object{
        @Provides
        fun provideGetMoviesInteractor(repository: MovieRepository) =
            GetMoviesInteractor(repository)
    }
}