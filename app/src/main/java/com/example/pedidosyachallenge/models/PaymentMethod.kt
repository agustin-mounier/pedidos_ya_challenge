package com.example.pedidosyachallenge.models

import io.realm.RealmObject

open class PaymentMethod(
    var id: String = "",
    var descriptionPT: String = "",
    var descriptionES: String = "",
    var online: Boolean = false
) : RealmObject()