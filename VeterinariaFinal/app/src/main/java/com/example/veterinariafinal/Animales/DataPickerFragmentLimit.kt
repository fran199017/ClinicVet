package com.example.veterinariafinal.pkAnimales

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragmentLimit : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener?= null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Coge la fecha actual del calendario
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Selected date (initial value)
        val datePickerDialog = DatePickerDialog(activity!!, listener, year, month, day)

        // Min and max date
        c.set(Calendar.YEAR, year - 20)
        datePickerDialog.datePicker.minDate = c.timeInMillis
        c.set(Calendar.YEAR, year - 0)
        datePickerDialog.datePicker.maxDate = c.timeInMillis

        return datePickerDialog
    }




    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragmentLimit {
            val fragment = DatePickerFragmentLimit()
            fragment.listener = listener
            return fragment
        }
    }


}