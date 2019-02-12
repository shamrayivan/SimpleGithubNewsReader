package com.lab.esh1n.github.base.pagination

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.*

/**
 * Created by esh1n on 3/19/18.
 */

abstract class PaginationRecyclerView<T>(private val loadMoreListener: () -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val TYPE_LOADING = 100
        private val TYPE_ITEM = 200
    }

    private val bottomEdgeScrollListener: BottomEdgeScrollListener = BottomEdgeScrollListener()

    protected var items: List<T> = listOf()

    private var canLoadMore = true
    private var isInProgress = false

    protected abstract fun getDiffUtilCallback(oldItems: List<T>, newItems: List<T>): DiffUtil.Callback
    protected abstract fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    protected abstract fun getLoadingStateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun showItems(items: List<T>) {
        if (this.items.isEmpty()) {
            this.items = items
            notifyDataSetChanged()
        } else {
            val oldList = this.items
            this.items = items
            DiffUtil.calculateDiff(getDiffUtilCallback(oldList, this.items), true)
                    .dispatchUpdatesTo(this)
        }
    }

    fun updateLoadingState(inProgress: Boolean) {
        this.isInProgress = inProgress
        if (isInProgress) {
            notifyItemInserted(itemCount)
        } else {
            notifyItemRemoved(itemCount)
        }
    }

    fun onAllItemsLoaded() {
        canLoadMore = false
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(bottomEdgeScrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(bottomEdgeScrollListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) getItemViewHolder(parent) else getLoadingStateViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        val isLastItem = position == itemCount - 1

        return if (isLastItem && isInProgress) TYPE_LOADING else TYPE_ITEM
    }

    protected fun isItemType(position: Int): Boolean {
        return getItemViewType(position) == TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return when {
            items.isEmpty() && isInProgress -> 1
            items.isEmpty() && !isInProgress -> 0
            isInProgress -> items.size + 1
            else -> items.size
        }
    }

    private inner class BottomEdgeScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisiblePosition = getLastVisibleItemPosition(recyclerView.layoutManager)
            val itemCount = recyclerView.adapter!!.itemCount

            if (!isInProgress && canLoadMore && lastVisiblePosition == itemCount - 1) {
                loadMoreListener()
            }
        }

        private fun getLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager?): Int {
            if (layoutManager == null) throw IllegalArgumentException("LayoutManger can't be null")

            if (LinearLayoutManager::class.java.isAssignableFrom(layoutManager.javaClass)) {
                val linearLayoutManager = layoutManager as LinearLayoutManager
                return linearLayoutManager.findLastVisibleItemPosition()
            } else if (StaggeredGridLayoutManager::class.java.isAssignableFrom(layoutManager.javaClass)) {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                val into = staggeredGridLayoutManager.findLastVisibleItemPositions(null)
                val intoList = ArrayList<Int>()
                for (i in into) {
                    intoList.add(i)
                }
                return Collections.max(intoList)
            }

            throw RuntimeException("Unknown LayoutManager class: " + layoutManager.javaClass)
        }

    }
}