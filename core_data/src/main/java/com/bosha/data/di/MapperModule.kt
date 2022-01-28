package com.bosha.data.di

import com.bosha.data.dto.local.FavoriteMovieEntity
import com.bosha.data.dto.local.MovieEntity
import com.bosha.data.dto.remote.JsonActor
import com.bosha.data.dto.remote.JsonMovie
import com.bosha.data.dto.remote.JsonMovieDetails
import com.bosha.data.mappers.*
import com.bosha.domain.entities.Actor
import com.bosha.domain.entities.Movie
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.utils.Mapper
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