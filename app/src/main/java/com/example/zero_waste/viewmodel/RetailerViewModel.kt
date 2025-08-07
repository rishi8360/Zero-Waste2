package com.example.zero_waste.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zero_waste.data.model.Product
import com.example.zero_waste.data.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

open class RetailerViewModel : ViewModel() {

    private val productRepo = ProductRepository()
    var isLoading by mutableStateOf(false)
        private set

    // List of products for this retailer
    var productList by mutableStateOf<List<Product>>(emptyList())
        private set

    // Status update result
    var updateStatusResult by mutableStateOf<Boolean?>(null)
        private set
    var deleteProductResult by mutableStateOf<Boolean?>(null)
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
    fun loadProducts(retailerId: String) {
        isLoading = true
        productRepo.getProductsForRetailer(retailerId) { products ->
            productList = products
            isLoading = false
        }
    }

    // Update product status (e.g., discount or donate)
    fun updateStatus(productId: String, newStatus: String) {
        productRepo.updateProductStatus(productId, newStatus) { success ->
            updateStatusResult = success

            // Update UI state locally too
            if (success) {
                productList = productList.map {
                    if (it.id == productId) it.copy(status = newStatus) else it
                }
            }

            // Reset result after short delay
            viewModelScope.launch {
                delay(1500)
                updateStatusResult = null
            }
        }
    }

    fun deleteProduct(productId: Product) {
        productRepo.deleteProduct(
            product = productId,
            onResult = { success ->
                deleteProductResult = success
                // Reset result after delay so UI feedback doesn’t stick
                viewModelScope.launch {
                    delay(1500)
                    deleteProductResult = null
                }

            }
        )
    }
}
