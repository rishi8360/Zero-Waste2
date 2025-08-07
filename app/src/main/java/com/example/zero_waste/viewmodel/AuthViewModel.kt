package com.example.zero_waste.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zero_waste.data.model.User
import com.example.zero_waste.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

open class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepo = UserRepository()
    var loginState by mutableStateOf<Boolean?>(null)
    var currentUser by mutableStateOf<User?>(null)
    fun login(email: String, password: String, role: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                val user = User(uid, email, role)
                userRepo.saveUser(user) { success ->
                    if (success) {
                        currentUser = user
                        loginState = true
                    } else loginState = false
                }
            }
            .addOnFailureListener {
                loginState = false
            }
    }
    fun signUp(email: String, password: String, role: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                val user = User(uid, email, role)
                userRepo.saveUser(user) { success ->
                    if (success) {
                        currentUser = user
                        loginState = true
                    } else {
                        loginState = false
                    }
                }
            }
            .addOnFailureListener {
                loginState = false
            }
    }

    fun checkUserRole(onResult: (String?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return onResult(null)
        userRepo.getUser(uid) { user ->
            currentUser = user
            onResult(user?.role)
        }
    }
}