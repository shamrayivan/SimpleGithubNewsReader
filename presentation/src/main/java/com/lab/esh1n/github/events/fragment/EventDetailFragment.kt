package com.lab.esh1n.github.events.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lab.esh1n.github.R
import com.lab.esh1n.github.base.BaseObserver
import com.lab.esh1n.github.base.BaseVMFragment
import com.lab.esh1n.github.domain.base.ErrorModel
import com.lab.esh1n.github.events.EventModel
import com.lab.esh1n.github.events.viewmodel.EventDetailViewModel
import com.lab.esh1n.github.events.viewmodel.SharedEventViewModel
import com.lab.esh1n.github.utils.SnackbarBuilder
import com.lab.esh1n.github.utils.loadCircleImage
import com.lab.esh1n.github.utils.openUrl
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.item_photo.*

class EventDetailFragment : BaseVMFragment<EventDetailViewModel>() {
    override val viewModelClass = EventDetailViewModel::class.java
    override val layoutResource = R.layout.fragment_event_details
    private lateinit var sharedEventViewModel: SharedEventViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivLike.setOnClickListener { viewModel.onChangeLikeState() }
        viewModel.event.observe(viewLifecycleOwner, object : BaseObserver<EventModel>() {
            override fun onData(data: EventModel?) {
                btnOpenBrowser.setOnClickListener {
                    requireActivity().openUrl(data?.actorAvatar)
                }
                ivAvatar.loadCircleImage(data?.actorAvatar)
                ivLike.setImageResource(if (data?.isLiked == true) R.drawable.like_on else R.drawable.likeoff)
            }

            override fun onError(error: ErrorModel?) {
                SnackbarBuilder.buildErrorSnack(view!!, error?.message ?: "").show()
            }

        })
        sharedEventViewModel = ViewModelProvider(requireActivity()).get(SharedEventViewModel::class.java)
        sharedEventViewModel.eventId.observe(viewLifecycleOwner, { eventId ->
            if (eventId != null && eventId > 0) {
                viewModel.loadEvent(eventId)
            }
        })
    }


    companion object {
        fun newInstance() = EventDetailFragment()
    }

}