package com.lab.esh1n.github.di


import com.lab.esh1n.github.di.worker.WorkerScope
import com.lab.esh1n.github.events.worker.SyncAllDataWorker
import dagger.Subcomponent

@WorkerScope
@Subcomponent()
interface WorkerComponent {

    fun inject(worker: SyncAllDataWorker)

    @Subcomponent.Builder
    interface Builder {
        fun build(): WorkerComponent
    }
}
