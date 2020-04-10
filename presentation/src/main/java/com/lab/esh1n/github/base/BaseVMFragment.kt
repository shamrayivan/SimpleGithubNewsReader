package com.lab.esh1n.github.base

import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseVMFragment<VM : BaseViewModel> : BaseDIFragment() {

    abstract val viewModelClass: Class<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM
            by lazy {
                ViewModelProvider(this, viewModelFactory).get(viewModelClass)
            }

}