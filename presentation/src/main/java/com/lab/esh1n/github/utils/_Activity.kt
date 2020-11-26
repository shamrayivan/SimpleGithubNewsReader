package com.lab.esh1n.github.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Context?.startActivity() {
    this?.let {
        val intent = Intent(this, T::class.java)
        it.startActivity(intent)
    }
}

inline fun <reified T : Activity> Context?.startActivity(args: Bundle) {
    this?.let {
        val intent = Intent(this, T::class.java)
        intent.putExtras(args)
        it.startActivity(intent)
    }
}