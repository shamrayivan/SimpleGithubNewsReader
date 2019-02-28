package com.lab.esh1n.github.events.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseObserver
import com.lab.esh1n.github.base.BaseVMFragment
import com.lab.esh1n.github.databinding.FragmentEventDetailsBinding
import com.lab.esh1n.github.domain.base.ErrorModel
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.viewmodel.EventDetailViewModel
import com.lab.esh1n.github.events.viewmodel.SharedEventViewModel
import com.lab.esh1n.github.utils.SnackbarBuilder

class EventDetailFragment : BaseVMFragment<EventDetailViewModel>() {
    override val viewModelClass = EventDetailViewModel::class.java
    override val layoutResource = R.layout.fragment_event_details
    private var binding: FragmentEventDetailsBinding? = null
    private lateinit var sharedEventViewModel: SharedEventViewModel

    override fun setupView(rootView: View) {
        super.setupView(rootView)
        binding = DataBindingUtil.bind(rootView)
        binding?.tvLink?.setOnClickListener {
            SnackbarBuilder.buildSnack(view!!, "Open browser").show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.event.observe(this, object : BaseObserver<EventModel>() {
            override fun onData(data: EventModel?) {
                if (data != null) {
                    bindEventToView(data)
                }
            }

            override fun onError(error: ErrorModel?) {
                SnackbarBuilder.buildErrorSnack(view!!, error?.message ?: "").show()
            }

        })
        sharedEventViewModel = ViewModelProviders.of(requireActivity()).get(SharedEventViewModel::class.java)
        sharedEventViewModel.eventId.observe(this, Observer { eventId ->
            if (eventId != null && eventId > 0) {
                viewModel.loadEvent(eventId)
            }
        })
    }

    private fun bindEventToView(eventModel: EventModel) {
        binding?.let {
            it.event = eventModel
            it.executePendingBindings()
        }
    }

    companion object {
        fun newInstance() = EventDetailFragment()
    }

}