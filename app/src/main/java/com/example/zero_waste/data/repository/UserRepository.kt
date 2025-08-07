package com.example.zero_waste.data.repository

import com.example.zero_waste.data.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    fun saveUser(user: User, onResult: (Boolean) -> Unit) {
        firestore.collection("users").document(user.uid)
            .set(user)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
    fun getUser(uid: String, onResult: (User?) -> Unit) {
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    onResult(doc.toObject(User::class.java))
                } else onResult(null)
            }
            .addOnFailureListener { onResult(null) }
    }
}