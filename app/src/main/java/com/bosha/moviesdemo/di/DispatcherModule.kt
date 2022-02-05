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
    companion object{
        const val DISPATCHER_IO = "dispatcher_io"
        const val DISPATCHER_DEFAULT = "dispatcher_default"
    }

    //Main Dispatcher by default
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @Named(DISPATCHER_IO)
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Named(DISPATCHER_DEFAULT)
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}