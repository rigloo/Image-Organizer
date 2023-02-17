package com.rigosapps.imageorganizer.helpers

import java.text.SimpleDateFormat
import java.util.*

class TimeHelper {


    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"

        fun getCurrentTime(): Date {
            val time = Calendar.getInstance().time

            return time

        }

        fun getStringfromDate(date: Date): String {
            val formatter = SimpleDateFormat(DATE_FORMAT)
            val current = formatter.format(date)
            return current
        }

        fun getDateFromString(stringDate: String): Date {
            return SimpleDateFormat(DATE_FORMAT).parse(stringDate)
        }

    }


}