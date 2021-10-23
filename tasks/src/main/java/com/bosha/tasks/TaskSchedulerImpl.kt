package com.bosha.tasks

import android.annotation.SuppressLint
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bosha.domain.utils.TaskScheduler
import com.bosha.tasks.workers.LoadMovieWorker
import com.bosha.tasks.workers.NotifyWorker
import java.time.Duration
import javax.inject.Inject

class TaskSchedulerImpl @Inject constructor(
    private val workManager: WorkManager
): TaskScheduler{

    @SuppressLint("NewApi")
    override fun scheduleNotification(id: String, delay: Duration){
        val loadRequest = OneTimeWorkRequestBuilder<LoadMovieWorker>()
            .setInitialDelay(delay)
            .setInputData(workDataOf(LoadMovieWorker.KEY_INPUT_DATA_ID to id))
            .build()

        val notifyRequest = OneTimeWorkRequestBuilder<NotifyWorker>().build()

        workManager
            .beginWith(loadRequest)
            .then(notifyRequest)
            .enqueue()
    }

}
