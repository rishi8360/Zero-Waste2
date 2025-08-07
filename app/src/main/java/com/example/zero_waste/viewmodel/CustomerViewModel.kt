package com.example.zero_waste.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zero_waste.data.model.Product
import com.example.zero_waste.data.repository.ProductRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CustomerViewModel: ViewModel() {
    private val productRepo = ProductRepository()

    // List of products for this retailer
    var productList by mutableStateOf<List<Product>>(emptyList())
        private set
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDaysUntilExpiry(expiryDateString: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val expiryDate = LocalDate.parse(expiryDateString, formatter)
            val today = LocalDate.now()
            val daysBetween = ChronoUnit.DAYS.between(today, expiryDate)
            when {
                daysBetween > 0 -> "$daysBetween days left"
                daysBetween == 0L -> "Expires Today"
                else -> "Expired ${-daysBetween} days ago"
            }
        } catch (e: Exception) {
            "Invalid date"
        }
    }


    // Load all products for this retailer
    fun loadProducts() {
        productRepo.fetchAllProducts() { products ->
            productList = products
        }
    }

}