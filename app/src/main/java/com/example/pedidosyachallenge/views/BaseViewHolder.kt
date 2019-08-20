package com.example.pedidosyachallenge.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosyachallenge.models.Restaurant

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(restaurant: Restaurant)
}