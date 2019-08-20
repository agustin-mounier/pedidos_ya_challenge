package com.example.pedidosyachallenge.views

import android.view.View
import com.example.pedidosyachallenge.models.Restaurant
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantViewHolder(itemView: View): BaseViewHolder(itemView) {

    override fun bind(restaurant: Restaurant) {
        itemView.restaurant_name.text = restaurant.name

        var categories = ""
        restaurant.categories.forEach { categories += it.name + ", " }
        itemView.restaurant_categories.text = categories.dropLast(2).toUpperCase()
    }
}