package com.example.zero_waste.screens.retailerScreen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.ShoppingBag
import com.composables.icons.lucide.Tags
import com.composables.icons.lucide.User
import com.example.zero_waste.ui.theme.GreenGradientBrush
import com.example.zero_waste.ui.theme.LightZeroWasteColorScheme
import com.example.zero_waste.viewmodel.RetailerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetailerHomeScreen(
    navController: NavController,
    viewModel: RetailerViewModel,
    retailerId: String
) {
    val context = LocalContext.current
    val productList = viewModel.productList
    var isLoading by remember { mutableStateOf(true) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadProducts(retailerId)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightZeroWasteColorScheme.background)
    ) {
        Column {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GreenGradientBrush)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Retailer Dashboard",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Lucide.User,
                                    contentDescription = "User",
                                    modifier = Modifier.align(Alignment.Center),
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(LightZeroWasteColorScheme.onPrimary),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Welcome Retailer!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InventoryStat("Total Products", productList.size.toString())
                            Divider(
                                color = Color(0xFFE5E7EB),
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(1.dp)
                            )
                            InventoryStat("About to expire", "2")
                        }
                    }
                }

                Button(
                    onClick = { navController.navigate("addProduct/$retailerId") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(GreenGradientBrush, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Lucide.Plus,
                                contentDescription = "AddProduct",
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Product", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("My Inventory", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (productList.isEmpty()) {
                    Text("No products found.", color = Color.Gray)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productList) { product ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(LightZeroWasteColorScheme.onPrimary),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(com.example.zero_waste.R.drawable.ic_launcher_background),
                                            contentDescription = "Product Image",
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text("Name : ${product.name}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                            Text("Expiry : ${viewModel.calculateDaysUntilExpiry(product.expiryDate)}", fontSize = 14.sp)
                                            Text("Quantity : ${product.quantity}", fontSize = 14.sp)
                                            Text("Price : ${product.price}", fontSize = 14.sp)
                                            Text("Status : ${product.status}", fontSize = 14.sp)
                                            if(product.contentDescription.isNotEmpty()) {
                                                Text("Description : ${product.contentDescription}", fontSize = 14.sp)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = { viewModel.updateStatus(product.id, "discount") },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                                            contentPadding = PaddingValues()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(GreenGradientBrush, shape = RoundedCornerShape(8.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(Lucide.Tags, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                        Text("Mark", fontSize = 13.sp, color = Color.White)
                                                        Text("Discount", fontSize = 13.sp, color = Color.White)
                                                    }
                                                }
                                            }
                                        }

                                        Button(
                                            onClick = { viewModel.updateStatus(product.id, "donate") },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                                            contentPadding = PaddingValues()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(GreenGradientBrush, shape = RoundedCornerShape(8.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(Lucide.ShoppingBag, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                        Text("Mark", fontSize = 13.sp, color = Color.White)
                                                        Text("Donate", fontSize = 13.sp, color = Color.White)
                                                    }
                                                }
                                            }
                                        }

                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable { showDeleteDialog = true }
                                        )
                                        if (showDeleteDialog) {
                                            DeleteProductDialog(
                                                viewModel,
                                                onDismiss = {
                                                    showDeleteDialog = false
                                                    viewModel.loadProducts(retailerId)
                                                },
                                                product
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            viewModel.updateStatusResult?.let {
                Toast.makeText(
                    context,
                    if (it) "Status Updated" else "Failed to Update",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun InventoryStat(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontSize = 14.sp, color = Color(0xFF6B7280))
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}
