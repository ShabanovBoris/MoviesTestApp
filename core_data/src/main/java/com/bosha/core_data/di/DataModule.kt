package com.bosha.core_data.di

import com.bosha.core_data.datasource.local.LocalDataSource
import com.bosha.core_data.datasource.local.impl.MovieLocalDataSource
import com.bosha.core_data.datasource.remote.RemoteDataSource
import com.bosha.core_data.datasource.remote.impl.MoviesRemoteDataSource
import com.bosha.core_data.repositories.RepositoryMovieImpl
import com.bosha.core_domain.interactors.AddMoviesInteractor
import com.bosha.core_domain.interactors.DeleteMoviesInteractor
import com.bosha.core_domain.interactors.GetMoviesInteractor
import com.bosha.core_domain.interactors.SearchMoviesInteractor
import com.bosha.core_domain.repositories.MovieRepository
import com.bosha.feature_main.data.repository.PagingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindRepository(repositoryImpl: RepositoryMovieImpl): MovieRepository

    @Singleton
    @Binds
    fun bindPagingRepository(repositoryImpl: RepositoryMovieImpl): com.bosha.feature_main.data.repository.PagingRepository

    @Singleton
    @Binds
    fun bindRemoteDataSource(remoteDataSource: MoviesRemoteDataSource): RemoteDataSource

    @Singleton
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

        @Provides
        fun provideSearchMoviesInteractor(repository: MovieRepository) =
            SearchMoviesInteractor(repository)
    }
}