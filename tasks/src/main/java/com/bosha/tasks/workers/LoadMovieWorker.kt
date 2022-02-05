package com.bosha.tasks.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.bosha.core_domain.entities.MovieDetails
import com.bosha.core_domain.interactors.GetMoviesInteractor
import com.bosha.feature_schedule.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import logcat.LogPriority
import logcat.logcat
import java.util.*

@HiltWorker
class LoadMovieWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dispatcher: CoroutineDispatcher? = null,
    private val getMoviesInteractor: GetMoviesInteractor
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val KEY_INPUT_DATA_ID = "key_id_movie_data"
        const val KEY_OUTPUT_ARG = "key_id_movie_data1"

    }

    private val id = checkNotNull(inputData.getString(KEY_INPUT_DATA_ID))

    // Uses [Dispatchers.Default] by default
    override suspend fun doWork(): Result {
        val result = if (dispatcher == null) work()
        else withContext(dispatcher) {
            return@withContext work()
        }

        delay(10000)

        return if (result.isSuccess) {
            successResult(
                result.getOrNull()?.title,
                result.getOrNull()?.overview,
                result.getOrNull()?.imageBackdrop,
                result.getOrNull()?.id.toString(),
            )

        } else {
            Result.failure()
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    override suspend fun getForegroundInfo(): ForegroundInfo {
        createChannel()
        return ForegroundInfo(
            123,
            NotificationCompat.Builder(applicationContext, "NotificationSender.CHANNEL_ID")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_baseline_schedule_24)
                .setContentText("wait")
                .setContentTitle("Loading")
                .build()
        )
    }

    private fun createChannel() {
        if (NotificationManagerCompat.from(applicationContext)
                .getNotificationChannelCompat("NotificationSender.CHANNEL_ID") == null
        ) {
            NotificationChannelCompat.Builder(
                "NotificationSender.CHANNEL_ID",
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName("Loader")
                .setDescription("...")
                .build()
                .also {
                    NotificationManagerCompat.from(applicationContext).createNotificationChannel(it)
                }
        }
    }

    private suspend fun work(): kotlin.Result<MovieDetails> {
        return getMoviesInteractor.getDetailsById(id)
            .catch { cause ->
                logcat(LogPriority.ERROR) { cause.localizedMessage!! }
                cause.printStackTrace()
            }
            .single()
            .also {
                logcat { "${this::class.java.name} result is $it" }
            }
    }

    private fun successResult(vararg args: String?): Result {
        return Result.success(
            Data.Builder()
                .putStringArray(KEY_OUTPUT_ARG, args)
                .build()
        )
    }
}