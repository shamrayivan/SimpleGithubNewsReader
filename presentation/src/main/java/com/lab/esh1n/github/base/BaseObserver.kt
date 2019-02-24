package com.lab.esh1n.github.base

import androidx.lifecycle.Observer
import com.lab.esh1n.github.domain.base.ErrorModel
import com.lab.esh1n.github.domain.base.Resource

abstract class BaseObserver<T> : Observer<Resource<T>> {
    override fun onChanged(r: Resource<T>?) {
        if (r != null) {
            when {
                r.status == Resource.Status.SUCCESS -> {
                    onProgress(false)
                    onData(r.data)
                }
                r.status == Resource.Status.LOADING -> {
                    onProgress(true)
                }
                r.status == Resource.Status.ERROR -> {
                    onProgress(false)
                    onError(r.errorModel)
                }
                else -> {
                    onEmptyResourceEmission()
                }
            }
        } else {
            onEmptyResourceEmission()
        }
    }

    abstract fun onData(data: T?)
    open fun onEmptyResourceEmission() {}
    open fun onProgress(progress: Boolean) {}
    abstract fun onError(error: ErrorModel?)
}