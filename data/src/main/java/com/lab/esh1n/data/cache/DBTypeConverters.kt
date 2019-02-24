package com.lab.esh1n.data.cache

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by esh1n on 3/9/18.
 */

class DBTypeConverters {
    private val API_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())

    @TypeConverter
    fun stringToDate(date: String?): Date? {
        if (date.isNullOrBlank()) {
            return null
        }

        return try {
            API_DATE_FORMAT.parse(date)
        } catch (e: ParseException) {
            null
        }

    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return if (date == null) null else API_DATE_FORMAT.format(date)
    }
}
