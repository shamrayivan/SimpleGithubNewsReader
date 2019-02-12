package com.lab.esh1n.github.search.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : BaseView> {
    protected val subscriptions: CompositeDisposable = CompositeDisposable()
    protected var view : V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun dettachView() {
        this.view = null
        subscriptions.clear()
    }

    protected fun unSubscribeOnDestroy(disposable: Disposable) {
        subscriptions.add(disposable)
    }
}