package com.example.pedidosyachallenge.models

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.pedidosyachallenge.repository.remote.PedidosYaService.Companion.BaseImgUrl


data class Restaurant(
    val deliveryTimeMinMinutes: String,
    val marks: List<String>,
    val cityName:String,
    val sortingLogistics: Int,
    val allCategories: String,
    val sortingDistance: Double,
    val doorNumber: String,
    val minDeliveryAmount: Int,
    val shrinkingTags: String,
    val deliveryTimeOrder: Int,
    val description: String,
    val mandatoryPaymentAmount: Boolean,
    val sortingTalent: Int,
    val shippingAmountIsPercentage: Boolean,
    val sortingVip: Int,
    val opened: Int,
    val sortingConvertionRate: Int,
    val index: Int,
    val deliveryAreas: String,
    val favoritesCount: Int,
    val isNew: Boolean,
    val delivers: Boolean,
    val channels: List<Int>,
    val businessType: String,
    val nextHourClose: String,
    val hasOnlinePaymentMethods: Boolean,
    val homeVip: Boolean,
    val discount: Int,
    val deliveryTime: String,
    val food: Double,
    val deliveryTimeId: Int,
    val sortingNew: Int,
    val nextHour: String,
    val acceptsPreOrder: Boolean,
    val weighing: Double,
    val noIndex: Boolean,
    val restaurantRegisteredDate: String,
    val link: String,
    val sortingCategory: Int,
    val restaurantTypeId: Int,
    val generalScore: Double,
    val isGoldVip: Boolean,
    val topCategories: String,
    val id: Int,
    val distance: Double,
    val area: String,
    val name: String,
    val sortingReviews: Double,
    val coordinates: String,
    val maxShippingAmount: Int,
    val logo: String,
    val ratingScore: String,
    val deliveryTimeMaxMinutes: String,
    val deliveryType: String,
    val speed: Double,
    val favoriteByUser: Boolean,
    val deliveryZoneId: Int,
    val sortingOrderCount: Int,
    val sortingOnlinePayment: Int,
    val shippingAmount: Float,
    val sortingGroupOrderCount: Int,
    val address: String,
    val hasZone: Boolean,
    val stateId: Int,
    val favoriteByOrders: Boolean,
    val service: Double,
    val paymentMethods: String,
    val categories: List<Category>,
    val paymentMethodsList: List<PaymentMethod>,
    val headerImage: String
) {

    fun getHeaderImageGlideUrl(accessToken: String): GlideUrl {
        return GlideUrl(BaseImgUrl + headerImage, LazyHeaders.Builder()
                .addHeader("Authorization", accessToken)
                .build()
        )
    }
}