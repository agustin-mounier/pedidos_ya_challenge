package com.example.pedidosyachallenge.models

data class Category(
    val id: String,
    val sortingIndex: Int,
    val visible: Boolean,
    val percentage: Double,
    val name: String,
    val manuallySorted: Boolean,
    val state: Int,
    val image: String,
    val quantity: Int
)