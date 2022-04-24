package com.bosha.core_data.di

import com.bosha.core.Mapper
import com.bosha.core_data.dto.local.FavoriteMovieEntity
import com.bosha.core_data.dto.local.MovieEntity
import com.bosha.core_data.dto.remote.JsonActor
import com.bosha.core_data.dto.remote.JsonMovie
import com.bosha.core_data.dto.remote.JsonMovieDetails
import com.bosha.core_data.mappers.*
import com.bosha.core_domain.entities.Actor
import com.bosha.core_domain.entities.Movie
import com.bosha.core_domain.entities.MovieDetails
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MapperModuleBinds {

    @Binds
    fun provideNetworkActorMapper(impl: ActorNetworkMapper): Mapper<JsonActor, Actor>

    @Binds
    fun provideNetworkDetailsMapper(impl: DetailsNetworkMapper): Mapper<JsonMovieDetails, MovieDetails>

    @Binds
    fun provideNetworkMovieMapper(impl: MovieNetworkMapper): Mapper<JsonMovie, Movie>

    @Binds
    fun provideDbMovieMapper(impl: MovieDbMapper): Mapper<MovieEntity, Movie>

    @Binds
    fun provideDbFavoriteMapper(impl: FavoriteMovieDbMapper): Mapper<FavoriteMovieEntity, Movie>
}