package com.lab.esh1n.github.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel() : ViewModel() {

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    protected fun Disposable.disposeOnDestroy() {
        disposables.add(this)
    }
}