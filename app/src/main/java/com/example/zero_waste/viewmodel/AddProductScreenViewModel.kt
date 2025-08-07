package com.example.zero_waste.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zero_waste.data.model.Product
import com.example.zero_waste.data.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddProductScreenViewModel: ViewModel() {
    private val productRepo = ProductRepository()
    var addProductResult by mutableStateOf<Boolean?>(null)
        private set
    private val _productPicture = MutableStateFlow<List<Bitmap>>(emptyList())
    val productPicture =_productPicture.asStateFlow()
    fun addProduct(product: Product) {
        productRepo.addProduct(product) { success ->
            addProductResult = success
            // Reset result after delay so UI feedback doesn’t stick
            viewModelScope.launch {
                delay(1500)
                addProductResult = true
            }
        }
    }
    fun onTakePhoto(bitmap: Bitmap) {
        _productPicture.value = _productPicture.value + bitmap
    }
    fun resetAddProductResult() {
        addProductResult = null
    }

}