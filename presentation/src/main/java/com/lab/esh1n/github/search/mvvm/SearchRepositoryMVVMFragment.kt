package com.lab.esh1n.github.search.mvvm

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseFragment
import com.lab.esh1n.github.search.SearchRepositoriesAdapter
import kotlinx.android.synthetic.main.fragment_search_repository.*
import javax.inject.Inject

/**
 * Created by esh1n on 3/16/18.
 */

class SearchRepositoryMVVMFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val repositoryViewModel: SearchRepositoryViewModel
            by lazy {
                ViewModelProviders.of(this, viewModelFactory)
                        .get(SearchRepositoryViewModel::class.java)
            }

    private val repositoriesAdapter: SearchRepositoriesAdapter = SearchRepositoriesAdapter { repositoryViewModel.loadMore() }

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

        repositoryViewModel.observeSearchResult().observe(this, Observer {
            if (it.isEmpty()) {
                tvNoResultsMessage.visibility = View.VISIBLE
            } else {
                tvNoResultsMessage.visibility = View.GONE
                repositoriesAdapter.showItems(it)
            }
        })
        repositoryViewModel.observeLoadingState().observe(this, Observer { repositoriesAdapter.updateLoadingState(it) })
        repositoryViewModel.observeAllItemsLoadedState().observe(this, Observer { repositoriesAdapter.onAllItemsLoaded() })
        repositoryViewModel.observeErrorState().observe(this,
                Observer { Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show() })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                repositoryViewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}