package com.lab.esh1n.github.events

import android.os.Bundle
import android.view.View

import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseActivity
import com.lab.esh1n.github.events.fragment.EventsFragment
import com.lab.esh1n.github.utils.addSingleFragmentToContainer

/**
 * Created by esh1n on 3/16/18.
 */

class EventsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isPortraitModeMode = findViewById<View>(R.id.fragment_events) == null
        if (isPortraitModeMode) {
            addSingleFragmentToContainer(EventsFragment.newInstance())
        }
    }
}