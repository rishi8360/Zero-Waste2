package com.example.zero_waste

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zero_waste.screens.customerScreen.CustomerHomeScreen
import com.example.zero_waste.screens.login.LoginScreen
import com.example.zero_waste.screens.login.SignUpScreen
import com.example.zero_waste.screens.retailerScreen.AddProductScreen
import com.example.zero_waste.screens.retailerScreen.RetailerHomeScreen
import com.example.zero_waste.viewmodel.AddProductScreenViewModel
import com.example.zero_waste.viewmodel.AuthViewModel
import com.example.zero_waste.viewmodel.CustomerViewModel
import com.example.zero_waste.viewmodel.RetailerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    val authViewModel = remember { AuthViewModel() }

    NavHost(navController = navController, startDestination = "login") {

        // 🔐 LOGIN SCREEN
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                navController = navController // Pass this to enable navigation
            ) { role ->
                val userId = authViewModel.currentUser?.uid ?: ""
                if (role == "retailer") {
                    navController.navigate("retailerHome/$userId")
                } else {
                    navController.navigate("customerHome")
                }
            }
        }


        // 🛍️ RETAILER HOME + PRODUCT LIST
        composable("retailerHome/{retailerId}") { backStackEntry ->
            val retailerId = backStackEntry.arguments?.getString("retailerId") ?: ""
            val retailerViewModel: RetailerViewModel = viewModel()
            RetailerHomeScreen(
                navController = navController,
                viewModel = retailerViewModel,
                retailerId = retailerId
            )
        }

        // ➕ ADD PRODUCT SCREEN
        composable("addProduct/{retailerId}") { backStackEntry ->
            val retailerId = backStackEntry.arguments?.getString("retailerId") ?: ""
            val addProductScreenViewModel: AddProductScreenViewModel = viewModel()
            AddProductScreen(viewModel = addProductScreenViewModel, navController = navController, retailerId = retailerId)
        }

        // 👥 CUSTOMER HOME (coming on Day 5)
        composable("customerHome") {
            val customerViewModel: CustomerViewModel =viewModel ()
            CustomerHomeScreen(customerViewModel)
        }
        composable("signup") {
            SignUpScreen(viewModel = authViewModel) {
                // Navigate after successful signup
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }

    }
}


