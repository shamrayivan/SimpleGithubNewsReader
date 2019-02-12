package com.lab.esh1n.github.search.mvp

import com.lab.esh1n.github.search.RepositoryModel

interface SearchContract {
    interface View : BaseView {
        fun showProgress()
        fun hideProgress()
        fun showRepositories(repos: List<RepositoryModel>)
        fun onNoResults()
        fun onAllItemsLoaded()

    }
    abstract class Presenter : BasePresenter<View>() {
        abstract fun search(query: String)
        abstract fun loadMore()
    }
}