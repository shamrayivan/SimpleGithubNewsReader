package com.lab.esh1n.github.base

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}

class SchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun ui() = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
}

class TrampolineSchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}

fun <T> BaseSchedulerProvider.applySchedulersSingle(): SingleTransformer<T, T> {
    return SingleTransformer { upstream: Single<T> -> upstream.subscribeOn(io()).observeOn(ui()) }
}

fun BaseSchedulerProvider.applySchedulersCompletable(): CompletableTransformer {
    return CompletableTransformer { upstream: Completable -> upstream.subscribeOn(io()).observeOn(ui()) }
}

fun <T> BaseSchedulerProvider.applySchedulersObservable(): ObservableTransformer<T, T> {
    return applySchedulersObservable(false)
}

fun <T> BaseSchedulerProvider.applySchedulersObservable(delayError: Boolean): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream: Observable<T> -> upstream.subscribeOn(io()).observeOn(ui(), delayError) }
}

fun <T> BaseSchedulerProvider.applySchedulersMaybe(): MaybeTransformer<T, T> {
    return MaybeTransformer { upstream: Maybe<T> -> upstream.subscribeOn(io()).observeOn(ui()) }
}

fun <T> BaseSchedulerProvider.applySchedulersFlowable(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream: Flowable<T> -> upstream.subscribeOn(io()).observeOn(ui()) }
}