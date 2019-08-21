package com.example.pedidosyachallenge.views.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.pedidosyachallenge.models.Restaurant
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantViewHolder(itemView: View) : BaseViewHolder(itemView) {

    /**
     * No pude autenticarme con el servicio de imagenes provisto. Al pasarle el access token en el header de la req
     * me responde con "Authorization header is invalid -- one and only one ' ' (space) required". Agrego estas imagenes
     * para que se luzca un poco mas el diseño, la idea era que utilize la headerImage que viene en el restaurant dto.
     */
    private val imageUrls = listOf(
        "https://cdn.foodandwineespanol.com/2019/02/destacda-hamburguesa.jpg",
        "https://elguardianmdp.com.ar/wp-content/uploads/2019/07/pizza.jpg",
        "https://www.infobae.com/new-resizer/R9JrDLFS_SpLKe3cR-IrsS1cx_U=/750x0/filters:quality(100)/s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2018/06/15184859/sushi-.jpg",
        "http://finde.latercera.com/wp-content/uploads/2018/10/Parrilla-2-OK-900x600.jpg",
        "https://diariodegastronomia.com/wp-content/uploads/2018/02/La-comida-asi%C3%A1tica-entra-con-fuerza-en-la-franquicia-759x500.jpg",
        "https://assets.bonappetit.com/photos/58a34e1df12ac6e639bf24ae/16:9/w_2560,c_limit/argentinian-beef-empanadas.jpg"
    )

    override fun bind(restaurant: Restaurant) {
        itemView.restaurant_name.text = restaurant.name

        var categories = ""
        restaurant.categories.forEach { categories += it.name + ", " }
        itemView.restaurant_categories.text = categories.dropLast(2).toUpperCase()
        itemView.restaurant_rating.text = restaurant.ratingScore
        itemView.delivery_time.text = restaurant.deliveryTime

        Glide.with(itemView)
            .load(imageUrls.random())
            .into(itemView.restaurant_image)
    }
}