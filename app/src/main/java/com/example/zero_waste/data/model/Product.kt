package com.example.zero_waste.data.model

import android.accessibilityservice.GestureDescription

data class Product(
    val id: String = "",
    val name: String = "",
    val expiryDate: String = "",
    val quantity: Int = 0,
    val type: String = "", // e.g., "Food", "Clothing"
    val status: String = "available", // "available", "discount", or "donate"
    val retailerId: String = "",
    val contentDescription: String = "",
    val price : Int = 0,
)
