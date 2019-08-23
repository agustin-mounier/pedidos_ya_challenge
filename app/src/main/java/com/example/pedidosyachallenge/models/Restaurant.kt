package com.example.pedidosyachallenge.models

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.pedidosyachallenge.repository.remote.PedidosYaService.Companion.BaseImgUrl
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Restaurant(
    @PrimaryKey
    var id: Int = 0,
    var deliveryTimeMinMinutes: String = "",
    var marks: RealmList<String>? = null,
    var cityName:String = "",
    var sortingLogistics: Int = 0,
    var allCategories: String = "",
    var sortingDistance: Double = 0.0,
    var doorNumber: String = "",
    var minDeliveryAmount: Int = 0,
    var shrinkingTags: String = "",
    var deliveryTimeOrder: Int = 0,
    var description: String = "",
    var mandatoryPaymentAmount: Boolean = false,
    var sortingTalent: Int = 0,
    var shippingAmountIsPercentage: Boolean = false,
    var sortingVip: Int = 0,
    var opened: Int = 0,
    var sortingConvertionRate: Int = 0,
    var index: Int = 0,
    var deliveryAreas: String = "",
    var favoritesCount: Int = 0,
    var isNew: Boolean = false,
    var delivers: Boolean = false,
    var channels: RealmList<Int>? = null,
    var businessType: String = "",
    var nextHourClose: String = "",
    var hasOnlinePaymentMethods: Boolean = false,
    var homeVip: Boolean = false,
    var discount: Int = 0,
    var deliveryTime: String = "",
    var food: Double = 0.0,
    var deliveryTimeId: Int = 0,
    var sortingNew: Int = 0,
    var nextHour: String = "",
    var acceptsPreOrder: Boolean = false,
    var weighing: Double = 0.0,
    var noIndex: Boolean = false,
    var restaurantRegisteredDate: String = "",
    var link: String = "",
    var sortingCategory: Int = 0,
    var restaurantTypeId: Int = 0,
    var generalScore: Double = 0.0,
    var isGoldVip: Boolean = false,
    var topCategories: String = "",
    var distance: Double = 0.0,
    var area: String = "",
    var name: String = "",
    var sortingReviews: Double = 0.0,
    var coordinates: String = "",
    var maxShippingAmount: Int = 0,
    var logo: String = "",
    var ratingScore: String = "",
    var deliveryTimeMaxMinutes: String = "",
    var deliveryType: String = "",
    var speed: Double = 0.0,
    var favoriteByUser: Boolean = false,
    var deliveryZoneId: Int = 0,
    var sortingOrderCount: Int = 0,
    var sortingOnlinePayment: Int = 0,
    var shippingAmount: Float = 0f,
    var sortingGroupOrderCount: Int = 0,
    var address: String = "",
    var hasZone: Boolean = false,
    var stateId: Int = 0,
    var favoriteByOrders: Boolean = false,
    var service: Double = 0.0,
    var paymentMethods: String = "",
    var categories: RealmList<Category>? = null,
    var paymentMethodsList: RealmList<PaymentMethod>? = null,
    var headerImage: String = ""
): RealmObject() {

    fun getHeaderImageGlideUrl(accessToken: String = ""): GlideUrl {
        return GlideUrl(BaseImgUrl + headerImage, LazyHeaders.Builder()
                .addHeader("Authorization", accessToken)
                .build()
        )
    }
}