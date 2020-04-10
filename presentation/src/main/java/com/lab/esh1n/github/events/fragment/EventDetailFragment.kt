package com.lab.esh1n.github.events.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseObserver
import com.lab.esh1n.github.base.BaseVMFragment
import com.lab.esh1n.github.databinding.FragmentEventDetailsBinding
import com.lab.esh1n.github.domain.base.ErrorModel
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.viewmodel.EventDetailViewModel
import com.lab.esh1n.github.events.viewmodel.SharedEventViewModel
import com.lab.esh1n.github.utils.SnackbarBuilder
import com.lab.esh1n.github.utils.loadCircleImage
import com.lab.esh1n.github.utils.openUrl
import com.lab.esh1n.github.utils.setVisibleOrGone

class EventDetailFragment : BaseVMFragment<EventDetailViewModel>() {
    override val viewModelClass = EventDetailViewModel::class.java
    override val layoutResource = R.layout.fragment_event_details
    private var binding: FragmentEventDetailsBinding? = null
    private lateinit var sharedEventViewModel: SharedEventViewModel

    override fun setupView(rootView: View) {
        super.setupView(rootView)
        binding = DataBindingUtil.bind(rootView)
        binding?.let {
            it.tvLink.setOnClickListener { _ ->
                requireActivity().openUrl(it.event?.repositoryLink)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.event.observe(viewLifecycleOwner, object : BaseObserver<EventModel>() {
            override fun onData(data: EventModel?) {
                if (data != null) {
                    showContent()
                    bindEventToView(data)
                } else {
                    showEmptyView()
                }
            }

            override fun onError(error: ErrorModel?) {
                SnackbarBuilder.buildErrorSnack(view!!, error?.message ?: "").show()
            }

        })
        sharedEventViewModel = ViewModelProvider(requireActivity()).get(SharedEventViewModel::class.java)
        sharedEventViewModel.eventId.observe(viewLifecycleOwner, Observer { eventId ->
            if (eventId != null && eventId > 0) {
                viewModel.loadEvent(eventId)
            }
        })
    }

    private fun showEmptyView() {
        binding?.let {
            it.viewEmpty.setVisibleOrGone(true)
            it.viewContent.setVisibleOrGone(false)
        }

    }

    private fun showContent() {
        binding?.let {
            it.viewEmpty.setVisibleOrGone(false)
            it.viewContent.setVisibleOrGone(true)
        }
    }

    private fun bindEventToView(eventModel: EventModel) {
        binding?.let {
            it.event = eventModel
            it.ivAvatar.loadCircleImage(eventModel.actorAvatar)
            it.executePendingBindings()
        }
    }

    companion object {
        fun newInstance() = EventDetailFragment()
    }

}