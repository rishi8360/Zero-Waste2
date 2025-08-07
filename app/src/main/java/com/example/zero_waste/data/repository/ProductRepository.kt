package com.example.zero_waste.data.repository

import android.util.Log
import com.example.zero_waste.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    fun addProduct(product: Product, onResult: (Boolean) -> Unit) {
        val docRef = productsCollection.document()
        val productWithId = product.copy(id = docRef.id)
        docRef.set(productWithId)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun deleteProduct(product: Product, onResult: (Boolean) -> Unit) {
        val docId = product.id
        if (docId.isNotEmpty()) {
            productsCollection.document(docId)
                .delete()
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener { exception ->
                    onResult(false)
                }
        } else {
            onResult(false)
        }
    }

    fun getProductsForRetailer(
        retailerId: String,
        onResult: (List<Product>) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("products")
            .whereEqualTo("retailerId", retailerId)
            .get()
            .addOnSuccessListener { result ->
                val products = result.toObjects(Product::class.java)
                onResult(products)
            }
    }

    fun updateProductStatus(
        productId: String,
        status: String,
        onResult: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("products")
            .document(productId)
            .update("status", status)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun fetchAllProducts(
        onResult: (List<Product>) -> Unit

    ) {
        FirebaseFirestore.getInstance()
            .collection("products")
            .get()
            .addOnSuccessListener { result ->
                val products = result.toObjects(Product::class.java)
                onResult(products)
            }
            .addOnFailureListener {
                // Handle error
            }
    }

}

