package com.bosha.feature_detail.utils.datetime

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import logcat.logcat
import java.time.Duration
import java.util.*

fun <T : Fragment> T.schedule(action: (Duration) -> Unit) {
    DateTime(requireContext()).apply {
        showPicker(childFragmentManager)
        doOnDurationCalculated = action
    }
}

class DateTime(parentContext: Context) {

    private var datePicker: DatePickerFragment? = null
    private var timePicker: TimePickerFragment? = null

    private var date: DatePickerFragment.Date? = null
    private var time: TimePickerFragment.Time? = null

    var doOnDurationCalculated: ((Duration) -> Unit)? = null

    init {
        datePicker = DatePickerFragment(parentContext)
        timePicker = TimePickerFragment(parentContext).apply {
            doOnTimeSet {
                time = it
                calculateDuration()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun calculateDuration() {
        val date = requireNotNull(date)
        val time = requireNotNull(time)
//        1.days.inWholeHours

        val timeNow = Calendar.getInstance().timeInMillis

        val timeChosen = Calendar.Builder()
            .setDate(date.year, date.month, date.day)
            .setTimeOfDay(time.hourOfDay, time.minute, 0)
            .build()
            .timeInMillis


        logcat {
            """
                Schedule movie on ${date.day}/${date.month} ${time.hourOfDay}:${time.minute}
                Before notify ${(timeChosen - timeNow)} millis
            """.trimIndent()
        }

        if (timeChosen > timeNow) {
            val delay = timeChosen - timeNow
            onDurationCalculated(Duration.ofMillis(delay))
        }
    }

    private fun onDurationCalculated(duration: Duration) {
        doOnDurationCalculated?.invoke(duration)
    }

    fun showPicker(fragmentManager: FragmentManager) {
        datePicker?.apply {
            show(fragmentManager, null)
            doOnDataSet {
                date = it
                timePicker?.show(fragmentManager, null)
            }
        }
    }
}