package com.example.pedidosyachallenge.views.viewholders

import android.preference.PreferenceManager
import android.view.View
import com.bumptech.glide.Glide
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    override fun bind(restaurant: Restaurant) {
        itemView.restaurant_name.text = restaurant.name

        var categories = ""
        restaurant.categories.forEach { categories += it.name + ", " }
        itemView.restaurant_categories.text = categories.dropLast(2).toUpperCase()

        val accessToken = prefs.getString(LoginViewModel.AccessToken, "")
        Glide.with(itemView)
            .load(restaurant.getHeaderImageGlideUrl(accessToken))
            .into(itemView.restaurant_image)
    }
}