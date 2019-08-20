package com.example.pedidosyachallenge.views

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollViewListener(private val mLayoutManager: LinearLayoutManager, private val loadData: () -> Unit) :
    RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private var previousTotalItemCount = 0
    private var loading = true
    private var enabled = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!enabled) {
            return
        }

        val totalItemCount = mLayoutManager.itemCount
        val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()

        // List was invalidated. Reset state.
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // Finished loading new items.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // Time to load more items. The threshold was breached.
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            loadData()
            loading = true
        }
    }

    fun resetState() {
        this.previousTotalItemCount = 0
        this.loading = true
    }

    fun setEnable(bool: Boolean) {
        this.enabled = bool
    }
}