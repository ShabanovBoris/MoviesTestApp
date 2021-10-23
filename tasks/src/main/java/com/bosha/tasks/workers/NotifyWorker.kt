package com.bosha.tasks.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.Coil
import coil.request.ImageRequest
import com.bosha.tasks.NotificationSender
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(appContext, workerParameters) {

    private val args = checkNotNull(inputData.getStringArray(LoadMovieWorker.KEY_OUTPUT_ARG))

    override suspend fun doWork(): Result {

        Coil.imageLoader(appContext)
            .execute(
                getImageRequest()
            )
            .drawable?.also {
                NotificationSender(appContext).show(args[0], args[1], it, args[3])
            }

        return Result.success()
    }

    private fun getImageRequest(): ImageRequest =
        ImageRequest.Builder(appContext)
            .data(args[2])
            .build()
}

