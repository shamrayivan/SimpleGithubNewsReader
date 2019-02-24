package com.lab.esh1n.github.events.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lab.esh1n.github.R

import com.lab.esh1n.github.base.BaseObserver
import com.lab.esh1n.github.base.BaseVMFragment
import com.lab.esh1n.github.databinding.FragmentEventsBinding
import com.lab.esh1n.github.domain.base.ErrorModel
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.EventsAdapter
import com.lab.esh1n.github.utils.SnackbarBuilder
import com.lab.esh1n.github.utils.setVisibleOrGone

/**
 * Created by esh1n on 3/16/18.
 */

class EventsFragment : BaseVMFragment<EventsViewModel>() {
    override val viewModelClass = EventsViewModel::class.java

    override val layoutResource = R.layout.fragment_events

    private var binding: FragmentEventsBinding? = null

    private lateinit var adapter: EventsAdapter

    override fun setupView(rootView: View) {
        super.setupView(rootView)
        binding = DataBindingUtil.bind(rootView)
        binding?.let {
            adapter = EventsAdapter(this::onEventClick)
            it.rvEvents.layoutManager = LinearLayoutManager(requireActivity())
            it.rvEvents.setHasFixedSize(true)
            it.rvEvents.adapter = adapter
        }

    }

    private fun onEventClick(eventModel: EventModel) {
        //activity.addFragmentToStack(ProfileByIdFragment.newInstance(userModel.id))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeUsers()
        viewModel.loadEvents()
    }

    private fun observeUsers() {

        viewModel.events.observe(this, object : BaseObserver<List<EventModel>>() {
            override fun onData(data: List<EventModel>?) {

                val isEmpty = data?.isEmpty() ?: true
                val emptyView = binding?.tvNoResultsMessage!!
                emptyView.setVisibleOrGone(isEmpty)
                if (isEmpty) {
                    emptyView.text = getString(R.string.text_no_events)
                }
                adapter.swapEvents(data ?: emptyList())

            }

            override fun onError(error: ErrorModel?) {
                SnackbarBuilder.buildErrorSnack(view!!, error?.message!!).show()
            }

            override fun onProgress(progress: Boolean) {
                super.onProgress(progress)
                //binding?.loadingIndicator?.setVisibleOrGone(progress)
            }
        })

    }

    companion object {
        fun newInstance() = EventsFragment()
    }

}