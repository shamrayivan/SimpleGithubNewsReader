package com.lab.esh1n.github.events

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lab.esh1n.data.cache.entity.EventEntity
import com.lab.esh1n.github.R
import com.lab.esh1n.github.databinding.ItemEventBinding
import com.lab.esh1n.github.utils.inflate
import com.lab.esh1n.github.utils.loadCircleImage
import kotlin.reflect.KProperty0

/**
 * Created by esh1n on 3/18/18.
 */

class EventsAdapter(private val clickHandler: (Long) -> Unit, private val mapper: KProperty0<(EventEntity) -> EventModel>) :
        PagedListAdapter<EventEntity, EventsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context.inflate(R.layout.item_event, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventEntity = getItem(position)
        eventEntity?.let {
            holder.populate(mapper.get()(it))
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val binding: ItemEventBinding? = DataBindingUtil.bind(itemView)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val event = getItem(adapterPosition)
            event?.let {
                clickHandler(it.id)
            }

        }

        fun populate(eventModel: EventModel?) {
            if (eventModel != null && binding != null) {
                binding.event = eventModel
                binding.executePendingBindings()
                binding.ivAvatar.loadCircleImage(eventModel.actorAvatar)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}