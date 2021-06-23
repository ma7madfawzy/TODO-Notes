package com.todo.notes.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("formatDate")
    @JvmStatic
    //converts date in format such as "2021-6-7 7:8"
    fun formatDate(view: TextView?, text: String?): String? {
        text?.let {
            val input = SimpleDateFormat("yyyy-M-d H:m")
            val output = SimpleDateFormat("yyyy-MM-dd HH:mm")

            try {
                val d = input.parse(text)
                val text = output.format(d!!)
                view?.text = text
                return text
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("formatShortDate")
    @JvmStatic

    //converts date in format such as "2021-6-7 7:8"
    fun formatShortDate(view: TextView, text: String?) {
        text?.let {
            val input = SimpleDateFormat("yyyy-M-d H:m")
            val output = SimpleDateFormat("EEE dd MM")

            try {
                val d = input.parse(text)
                view.text = output.format(d!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("formatDateBigDate")
    @JvmStatic
    //converts date in format such as "2021-06-07T14:44:06Z"
    fun formatDateBigDate(view: TextView, text: String?) {
        text?.let {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val output = SimpleDateFormat("MMM dd yyyy")

            try {
                val d = input.parse(text)
                view.text = output.format(d!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    // @date format must be like 2021-05-08 07:13
    fun calendar(date: String?): Long? {
        return try {
            SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun calendar(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int): Calendar {
        val datetime = Calendar.getInstance()
        datetime[Calendar.DAY_OF_MONTH] = year
        datetime[Calendar.MONTH] = month - 1
        datetime[Calendar.HOUR_OF_DAY] = year
        datetime[Calendar.HOUR_OF_DAY] = hourOfDay
        datetime[Calendar.MINUTE] = minute
        return datetime
    }
}