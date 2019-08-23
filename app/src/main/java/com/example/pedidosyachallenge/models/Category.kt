package com.example.pedidosyachallenge.models

import io.realm.RealmObject

open class Category(
    var id: String = "",
    var sortingIndex: Int = 0,
    var visible: Boolean = false,
    var percentage: Double = 0.0,
    var name: String = "",
    var manuallySorted: Boolean = false,
    var state: Int = 0,
    var image: String = "",
    var quantity: Int = 0
): RealmObject()