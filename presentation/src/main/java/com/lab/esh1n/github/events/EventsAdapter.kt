package com.lab.esh1n.github.events

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lab.esh1n.github.R
import com.lab.esh1n.github.databinding.ItemEventBinding
import com.lab.esh1n.github.utils.inflate
import com.lab.esh1n.github.utils.loadCircleImage

/**
 * Created by esh1n on 3/18/18.
 */

class EventsAdapter(private val clickHandler: (EventModel) -> Unit) :
        RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var events: List<EventModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context.inflate(R.layout.item_event, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = events[position]
        holder.populate(person)
    }

    override fun getItemCount() = events.size

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val binding: ItemEventBinding? = DataBindingUtil.bind(itemView)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val userModel = events[adapterPosition]
            clickHandler.invoke(userModel)
        }

        fun populate(eventModel: EventModel?) {
            if (eventModel != null && binding != null) {
                binding.event = eventModel
                binding.executePendingBindings()
                binding.ivAvatar.loadCircleImage(eventModel.actorAvatar)
            }
        }
    }

    fun swapEvents(newUsers: List<EventModel>) {
        if (!this.events.isEmpty()) {
            val result = DiffUtil.calculateDiff(DiffUtilCallback(this.events, newUsers))
            this.events = newUsers
            result.dispatchUpdatesTo(this)
        } else {
            this.events = newUsers
            notifyDataSetChanged()
        }
    }

    private class DiffUtilCallback(private val oldList: List<EventModel>,
                                   private val newList: List<EventModel>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}