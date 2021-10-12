package com.bosha.domain.utils

import java.time.Duration

interface TaskScheduler {
    fun scheduleNotification(id: String, delay: Duration)
}