package com.lab.esh1n.github.search.mvp

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.lab.esh1n.github.search.SearchRepositoriesAdapter
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseFragment
import com.lab.esh1n.github.base.model.ErrorModel
import com.lab.esh1n.github.search.RepositoryModel
import kotlinx.android.synthetic.main.fragment_search_repository.*
import javax.inject.Inject

class SearchRepositoryMVPFragment : BaseFragment(), SearchContract.View {

    @Inject
    lateinit var presenter: SearchRepositoryPresenter

    private val repositoriesAdapter: SearchRepositoriesAdapter = SearchRepositoriesAdapter { presenter.loadMore() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRepositories.adapter = repositoriesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.dettachView()
    }

    override fun showProgress() {
        repositoriesAdapter.updateLoadingState(true)
    }

    override fun hideProgress() {
        repositoriesAdapter.updateLoadingState(false)
    }

    override fun showRepositories(repos: List<RepositoryModel>) {
        repositoriesAdapter.showItems(repos)
        tvNoResultsMessage.visibility = View.GONE
    }

    override fun onNoResults() {
        tvNoResultsMessage.visibility = View.VISIBLE
    }

    override fun onAllItemsLoaded() {
        repositoriesAdapter.onAllItemsLoaded()
    }

    override fun onError(error: ErrorModel) {
        Toast.makeText(activity, error.message, Toast.LENGTH_LONG).show()
    }

}