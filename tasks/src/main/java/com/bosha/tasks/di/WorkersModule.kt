package com.bosha.tasks.di

import android.content.Context
import androidx.work.WorkManager
import com.bosha.core.TaskScheduler
import com.bosha.tasks.TaskSchedulerImpl
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
    fun bindScheduler(implTask: TaskSchedulerImpl): TaskScheduler

    companion object {
        @Provides
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
            WorkManager.getInstance(context)
    }
}