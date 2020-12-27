package com.lab.esh1n.github.events

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shamray.lab.cache.entity.PhotoEntity
import com.lab.esh1n.github.R
import com.lab.esh1n.github.utils.inflate
import com.lab.esh1n.github.utils.loadCircleImage
import kotlinx.android.synthetic.main.item_photo.view.*
import kotlin.reflect.KProperty0

class EventsAdapter(private val clickHandler: (Long) -> Unit, private val mapper: KProperty0<(PhotoEntity) -> EventModel>,
                    private val likeListener: (PhotoEntity) -> Unit) :
        PagedListAdapter<PhotoEntity, EventsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context.inflate(R.layout.item_photo, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventEntity = getItem(position)
        eventEntity?.let {
            holder.populate(mapper.get()(it))
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
            view.ivLike.setOnClickListener {
                val event = getItem(adapterPosition)
                event?.let {
                    likeListener(it)
                }
            }
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val event = getItem(adapterPosition)
            event?.let {
                clickHandler(it.id)
            }

        }

        fun populate(eventModel: EventModel?) {
            if (eventModel != null) {
                itemView.ivLike.setImageResource(if (eventModel.isLiked) R.drawable.like_on else R.drawable.likeoff)
                itemView.ivAvatar.loadCircleImage(eventModel.actorAvatar)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PhotoEntity> = object : DiffUtil.ItemCallback<PhotoEntity>() {
            override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}