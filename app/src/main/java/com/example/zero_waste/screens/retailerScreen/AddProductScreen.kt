package com.example.zero_waste.screens.retailerScreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Eraser
import com.composables.icons.lucide.IndianRupee
import com.composables.icons.lucide.Lucide
import com.example.zero_waste.data.model.Product
import com.example.zero_waste.ui.theme.GreenGradientBrush
import com.example.zero_waste.ui.theme.LightZeroWasteColorScheme
import com.example.zero_waste.viewmodel.AddProductScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductScreenViewModel, navController: NavController,
    retailerId: String
) {
    val CAMERAX_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    var showAddPhotoDialogBox by remember { mutableStateOf(false) }
    var showDatePickerDialogBox by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var name by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var contentDescription by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Food") }
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context.applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf9fafb))
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp)
                .background(brush = GreenGradientBrush)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))

        ) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    IconButton(onClick = { navController.navigate("retailerHome/$retailerId") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Add New Product",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        CardSection(title = "Product Name") {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter product name", fontSize = 16.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Lucide.IndianRupee,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        CardSection(title = "Product Details") {

            // Add Photo Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .border(1.dp, Color(0xFF81C784), RoundedCornerShape(12.dp))
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                    .clickable(enabled = true, onClick = {
                        cameraPermissionState.launchPermissionRequest()
                        showAddPhotoDialogBox = true
                    }),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Lucide.Camera,
                            contentDescription = "Add Photo",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Add Photo", color = Color(0xFF4CAF50), fontSize = 16.sp)
                }
            }
            if (cameraPermissionState.status.isGranted && showAddPhotoDialogBox) {
                AddPhotoDialog(
                    controller,
                    onDismiss = { showAddPhotoDialogBox = false },
                    onImageCaptured = {})
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date Field
            TextFieldLabel("Date Added")
            OutlinedTextField(
                value = expiryDate,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Date", fontSize = 16.sp) },
                leadingIcon = {
                    Icon(imageVector = Lucide.Calendar, contentDescription = "Calendar")
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePickerDialogBox = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker"
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
            if (showDatePickerDialogBox) {
                DatePickerField(
                    onDateSelected = { expiryDate = it },
                    onDismiss = { showDatePickerDialogBox = false })

            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quantity Field
            TextFieldLabel("Quantity")
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                placeholder = { Text("Enter quantity", fontSize = 16.sp) },
                leadingIcon = {
                    Icon(imageVector = Lucide.Eraser, contentDescription = "Quantity")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            Spacer(modifier = Modifier.height(12.dp))

            // Product Type (Dropdown Style)
            TextFieldLabel("Product Type")
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                placeholder = { Text("Food", fontSize = 16.sp) },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Description Field
        CardSection(title = "Description") {
            OutlinedTextField(
                value = contentDescription,
                onValueChange = { contentDescription = it },
                placeholder = { Text("Enter product description (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Price Card
        CardSection(title = "Price") {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                placeholder = { Text("0.00") },
                leadingIcon = {
                    Icon(imageVector = Lucide.IndianRupee, contentDescription = "Indian Rupee")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Submit Button
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(50))
                .background(GreenGradientBrush)
                .clickable {
                    if (name.isNotEmpty() && expiryDate.isNotEmpty() && quantity.isNotEmpty() && price.isNotEmpty()) {
                        viewModel.addProduct(
                            Product(
                                name = name,
                                expiryDate = expiryDate,
                                quantity = quantity.toInt(),
                                type = type,
                                retailerId = retailerId,
                                contentDescription = contentDescription,
                                price = price.toInt()
                            )
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add Product",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        LaunchedEffect(viewModel.addProductResult) {
            if (viewModel.addProductResult == true) {
                Toast.makeText(context, "Added Product", Toast.LENGTH_SHORT).show()
                name = ""
                expiryDate = ""
                quantity = ""
                type = ""
                contentDescription = ""
                price = ""
                viewModel.resetAddProductResult()
                navController.navigate("retailerHome/$retailerId")
            }
        }


    }
    fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}

@Composable
private fun CardSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun TextFieldLabel(text: String) {
    Text(text, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color(0xFF4B5563))
    Spacer(modifier = Modifier.height(4.dp))
}
