package com.bosha.moviesdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    //by default
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Named("IO")
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Named("DEFAULT")
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}