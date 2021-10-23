package com.bosha.tasks.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.bosha.domain.entities.MovieDetails
import com.bosha.domain.interactors.GetMoviesInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import logcat.LogPriority
import logcat.logcat

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