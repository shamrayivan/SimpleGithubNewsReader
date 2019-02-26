package com.lab.esh1n.github

import android.app.Activity
import android.app.Application
import android.content.Context
import com.lab.esh1n.github.di.AppComponent
import com.lab.esh1n.github.di.DaggerAppComponent
import com.lab.esh1n.github.di.WorkerComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by esh1n on 3/9/18.
 */

class GithubApp : Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var appComponent: AppComponent
    private lateinit var workerComponent: WorkerComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()
        workerComponent = appComponent.plusWorkerComponent().build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    companion object {
        fun getWorkerComponent(context: Context): WorkerComponent {
            return (context as GithubApp).workerComponent
        }
    }
}
