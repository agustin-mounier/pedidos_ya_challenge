package com.example.pedidosyachallenge.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.models.Restaurant

class RestaurantsAdapter (
    private val restaurants: LiveData<List<Restaurant>>,
    private val isLoadingPage: LiveData<Boolean>
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val RESTAURANT_TYPE = 0
        const val LOADING_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            RESTAURANT_TYPE -> {
                val view = layoutInflater.inflate(R.layout.restaurant_item_view, parent, false)
                RestaurantViewHolder(view)
            }
            LOADING_TYPE -> {
                val view = layoutInflater.inflate(R.layout.loading_item_view, parent, false)
                LoadingViewHolder(view)
            }
            else -> null as BaseViewHolder
        }
    }

    override fun getItemCount(): Int {
        return restaurants.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(restaurants.value!![position])
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoadingPage.value!!) {
            if (position == restaurants.value!!.size - 1)
                return LOADING_TYPE
            else
                return RESTAURANT_TYPE
        } else {
            return RESTAURANT_TYPE
        }
    }
}