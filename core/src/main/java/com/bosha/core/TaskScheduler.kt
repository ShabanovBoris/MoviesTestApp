package com.bosha.core

import java.time.Duration

interface TaskScheduler {
    fun scheduleNotification(id: String, delay: Duration)
}