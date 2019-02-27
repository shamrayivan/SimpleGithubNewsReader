package com.lab.esh1n.github.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lab.esh1n.github.R

private const val EVENT_KEYWORD = "Event"

@BindingAdapter("eventType")
fun setEventType(tv: TextView, type: String?) {
    tv.text = if (type == null) tv.context.getString(R.string.text_event_type_update) else getTypeDescription(type)
}

fun getTypeDescription(type: String): String {
    return if (type.contains(EVENT_KEYWORD)) {
        type.replace(EVENT_KEYWORD, "")
    } else {
        EVENT_KEYWORD
    }
}