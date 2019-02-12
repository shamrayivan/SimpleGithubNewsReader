package com.lab.esh1n.github.di.base

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lab.esh1n.github.search.mvvm.SearchRepositoryViewModel
import com.lab.esh1n.github.di.ViewModelKey
import com.lab.esh1n.github.di.search.SearchRepositoryModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by esh1n on 3/9/18.
 */
@Module(includes = [SearchRepositoryModule::class])
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(SearchRepositoryViewModel::class)
    abstract fun repositorySearchViewModel(repositoryViewModel: SearchRepositoryViewModel): ViewModel

    @Singleton
    @Binds
    abstract fun provideViewModelFractory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory @Inject
    constructor(private val creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

        @NonNull
        override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
            var creator: Provider<out ViewModel>? = creators[modelClass]
            if (creator == null) {
                for ((key, value) in creators) {
                    if (modelClass.isAssignableFrom(key)) {
                        creator = value
                        break
                    }
                }
            }
            if (creator == null) {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
            try {
                return creator.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }
}