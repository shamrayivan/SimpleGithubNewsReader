package com.lab.esh1n.github.search.mvp

import com.lab.esh1n.github.base.model.ErrorModel

interface BaseView {
    fun onError(error: ErrorModel)
}