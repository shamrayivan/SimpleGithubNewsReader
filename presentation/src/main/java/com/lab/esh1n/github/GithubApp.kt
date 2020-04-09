package com.lab.esh1n.github

import android.app.Application
import android.content.Context
import com.lab.esh1n.github.di.AppComponent
import com.lab.esh1n.github.di.DaggerAppComponent
import com.lab.esh1n.github.di.WorkerComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


/**
 * Created by esh1n on 3/9/18.
 */

class GithubApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

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


    companion object {
        fun getWorkerComponent(context: Context): WorkerComponent {
            return (context as GithubApp).workerComponent
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }
}
