package com.bosha.data.di

import com.bosha.data.datasource.local.LocalDataSource
import com.bosha.data.datasource.local.impl.MovieLocalDataSource
import com.bosha.data.datasource.remote.RemoteDataSource
import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource
import com.bosha.data.repositoryImpl.RepositoryImpl
import com.bosha.domain.interactors.AddMoviesInteractor
import com.bosha.domain.interactors.DeleteMoviesInteractor
import com.bosha.domain.interactors.GetMoviesInteractor
import com.bosha.domain.interactors.SearchMoviesInteractor
import com.bosha.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): MovieRepository

    @Binds
    fun bindRemoteDataSource(remoteDataSource: MoviesRemoteDataSource): RemoteDataSource

    @Binds
    fun bindLocalDataSource(localDataSource: MovieLocalDataSource): LocalDataSource

    companion object{
        @ViewModelScoped
        @Provides
        fun provideGetMoviesInteractor(repository: MovieRepository) =
            GetMoviesInteractor(repository)

        @ViewModelScoped
        @Provides
        fun provideAddMoviesInteractor(repository: MovieRepository) =
            AddMoviesInteractor(repository)

        @ViewModelScoped
        @Provides
        fun provideDeleteMoviesInteractor(repository: MovieRepository) =
            DeleteMoviesInteractor(repository)

        @ViewModelScoped
        @Provides
        fun provideSearchMoviesInteractor(repository: MovieRepository) =
            SearchMoviesInteractor(repository)
    }
}