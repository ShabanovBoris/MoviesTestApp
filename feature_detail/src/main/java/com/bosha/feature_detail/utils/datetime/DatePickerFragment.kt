package com.bosha.feature_detail.utils.datetime

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(private val parentContext: Context) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private var doOnDataSet: ((Date) -> Unit)? = null

    fun doOnDataSet(action: (Date) -> Unit) {
        doOnDataSet = action
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(parentContext, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        doOnDataSet?.invoke(Date(year, month, day))
    }

    data class Date(
        val year: Int,
        val month: Int,
        val day: Int
    )
}