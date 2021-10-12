package com.bosha.feature_detail.utils.datetime

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(private val parentContext: Context) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var doOnTimeSet: ((Time) -> Unit)? = null

    fun doOnTimeSet(action: (Time) -> Unit){
        doOnTimeSet = action
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(parentContext, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        doOnTimeSet?.invoke(Time(hourOfDay, minute))
    }

    data class Time(
        val hourOfDay: Int,
        val minute: Int
    )
}