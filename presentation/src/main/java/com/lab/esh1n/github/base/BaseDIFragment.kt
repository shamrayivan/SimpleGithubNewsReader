package com.lab.esh1n.github.base

import android.content.Context
import dagger.android.support.AndroidSupportInjection

/**
 * Created by esh1n on 3/16/18.
 */

abstract class BaseDIFragment : BaseFragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
