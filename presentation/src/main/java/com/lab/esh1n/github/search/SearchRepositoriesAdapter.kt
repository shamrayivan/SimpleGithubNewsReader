package com.lab.esh1n.github.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lab.esh1n.github.R
import com.lab.esh1n.github.utils.loadCircleImage
import com.lab.esh1n.github.base.pagination.PaginationRecyclerView
import kotlinx.android.synthetic.main.item_repository.view.*

/**
 * Created by esh1n on 3/18/18.
 */

class SearchRepositoriesAdapter(loadMoreCallback: () -> Unit)
    : PaginationRecyclerView<RepositoryModel>(loadMoreCallback) {

    override fun getDiffUtilCallback(oldItems: List<RepositoryModel>,
                                     newItems: List<RepositoryModel>): DiffUtil.Callback {
        return RepositoryDiffUtilCallback(oldItems, newItems)
    }

    override fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repository, parent, false)

        return ItemViewHolder(view)
    }

    override fun getLoadingStateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
        return LoadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isItemType(position)) {
            val itemViewHolder = holder as ItemViewHolder
            val repository = items[position]

            itemViewHolder.itemView.tvRepositoryName.text = repository.name
            itemViewHolder.itemView.tvForksCount.text = repository.forksCount.toString()
            itemViewHolder.itemView.tvStartCount.text = repository.starsCount.toString()
            itemViewHolder.itemView.tvLanguage.text = repository.language
            itemViewHolder.itemView.tvWatchCount.text = repository.watchersCount.toString()
            itemViewHolder.itemView.ivAvatar.loadCircleImage(repository.avatar)
        }
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class RepositoryDiffUtilCallback(private val oldList: List<RepositoryModel>,
                                             private val newList: List<RepositoryModel>) : DiffUtil.Callback() {

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