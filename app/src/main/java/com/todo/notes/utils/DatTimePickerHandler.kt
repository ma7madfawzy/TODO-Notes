package com.todo.notes.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.todo.notes.R
import java.util.*

class DatTimePickerHandler(private val textView: TextView) : TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {
    private var myDay = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0
    private val calendar: Calendar = Calendar.getInstance()

    init {
        showDatePicker()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myMonth = month + 1
        myDay = dayOfMonth
        showTimePicker()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (getCurrentCalendar(
                hourOfDay,
                minute
            ).timeInMillis >= Calendar.getInstance().timeInMillis
        )
            DateFormatUtil.formatDate(textView, "$myYear-$myMonth-$myDay $hourOfDay:$minute")
        else {
            Toast.makeText(view?.context, R.string.future_time_only, Toast.LENGTH_SHORT).show()
            showTimePicker()
        }
    }

    private fun getCurrentCalendar(hourOfDay: Int, minute: Int): Calendar {
        return DateFormatUtil.calendar(myYear, myMonth, myDay, hourOfDay, minute)
    }


    private fun showDatePicker() {
        val picker = DatePickerDialog(
            textView.context,
            DatePickerDialog.OnDateSetListener(this::onDateSet),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.datePicker.minDate = System.currentTimeMillis() - 1000
        picker.show()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            textView.context,
            this@DatTimePickerHandler,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(textView.context)
        )
        timePickerDialog.show()
    }
}