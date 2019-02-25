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
    private val UI_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())


    @TypeConverter
    fun longToDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    fun fromDate(date: Date): String {
        val formatted = UI_FORMATTER.get()!!.format(date)
        return formatted
    }

    fun stringToDate(iso8601string: String?): Date? {
        if (iso8601string.isNullOrBlank()) {
            return null
        }

        return try {
            toDate(iso8601string)
        } catch (e: ParseException) {
            null
        }

    }

    /** Transform ISO 8601 string to Calendar.  */
    @Throws(ParseException::class)
    fun toDate(iso8601string: String): Date {
        var s = iso8601string.replace("Z", "+00:00")
        try {
            s = s.substring(0, 22) + s.substring(23)  // to get rid of the ":"
        } catch (e: IndexOutOfBoundsException) {
            throw ParseException("Invalid length", 0)
        }
        val resultFromCustom = API_DATE_FORMAT.parse(s)

        //val resultFromVNative = tryParseDifferent(iso8601string);
        val iso8601strinC = fromDate(resultFromCustom)
        // val iso8601strinH = fromDate(resultFromVNative)
        return resultFromCustom
    }

    private fun tryParseDifferent(iso8601string: String): Date {
        return try {
            API_FORMATTER.get()!!.parse(iso8601string)
        } catch (e: ParseException) {
            Date()
        }
    }

    private val API_FORMATTER = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            return df
        }
    }

    private val UI_FORMATTER = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            df.timeZone = TimeZone.getDefault()
            return df
        }
    }


}
