package com.example.pedidosyachallenge.models

data class PaymentMethod(
    val id: String,
    val descriptionPT: String,
    val descriptionES: String,
    val online: Boolean
)