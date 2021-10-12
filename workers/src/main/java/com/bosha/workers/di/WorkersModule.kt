package com.bosha.workers.di

import android.content.Context
import androidx.work.WorkManager
import com.bosha.domain.utils.TaskScheduler
import com.bosha.workers.Scheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface WorkersModule {
    @Binds
    fun bindScheduler(impl: Scheduler): TaskScheduler

    companion object {
        @Provides
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
            WorkManager.getInstance(context)
    }
}