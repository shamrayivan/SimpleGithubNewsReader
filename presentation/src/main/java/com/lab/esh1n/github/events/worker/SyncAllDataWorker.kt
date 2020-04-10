package com.lab.esh1n.github.events.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.lab.esh1n.github.GithubApp
import com.lab.esh1n.github.domain.base.Resource
import com.lab.esh1n.github.domain.events.usecase.FetchAndSaveNewEventsUseCase
import com.lab.esh1n.github.utils.WORKER_ERROR_DESCRIPTION
import javax.inject.Inject


class SyncAllDataWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    @Inject
    lateinit var fetchAndSaveEventsUseCase: FetchAndSaveNewEventsUseCase


    override fun doWork(): Result {
        GithubApp.getWorkerComponent(applicationContext).inject(this)
        val syncResult = fetchAndSaveEventsUseCase.execute(Unit).blockingGet()
        return if (syncResult.status == Resource.Status.ERROR) {
            val failureResult = workDataOf(WORKER_ERROR_DESCRIPTION to syncResult.errorModel)
            Result.failure(failureResult)
        } else {
            Result.success()
        }
    }

}