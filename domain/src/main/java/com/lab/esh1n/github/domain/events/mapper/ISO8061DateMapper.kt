package com.lab.esh1n.github.domain.events.mapper

import com.lab.esh1n.github.domain.base.TwoWayMapper
import java.text.SimpleDateFormat
import java.util.*

class ISO8061DateMapper : TwoWayMapper<String, Date>() {


    override fun mapInverse(source: Date): String {
        return UI_FORMATTER.get()!!.format(source)
    }

    override fun map(source: String): Date {
        if (source.isBlank()) {
            return Date()
        }
        return tryToConvertUnstructuredDate(source)
    }

    private fun tryToConvertUnstructuredDate(iso8601string: String): Date {
        var s = iso8601string.replace("Z", "+00:00")
        return try {
            s = s.substring(0, 22) + s.substring(23)  // to get rid of the ":"
            API_DATE_FORMAT.get()!!.parse(s) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }


    companion object {
        private val API_DATE_FORMAT = object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
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
}




