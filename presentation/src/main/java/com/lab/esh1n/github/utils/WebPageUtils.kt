package com.lab.esh1n.github.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.openUrl(url: String?) {
    if (url.isNullOrBlank()) {
        return
    }
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}