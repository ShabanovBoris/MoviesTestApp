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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@HiltWorker
class NotifyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(appContext, workerParameters) {

    private val args = checkNotNull(inputData.getStringArray(LoadMovieWorker.KEY_OUTPUT_ARG))

    override suspend fun doWork(): Result {
        val image = coroutineScope {
            async {
                Coil.imageLoader(appContext).execute(
                    getImageRequest()
                ).drawable
            }
        }

        image.await()?.let {
            NotificationSender(appContext).show(args[0], args[1], it)
        }

        return Result.success()
    }

    private fun getImageRequest(): ImageRequest =
        ImageRequest.Builder(appContext)
            .data(args[2])
            .build()
}

