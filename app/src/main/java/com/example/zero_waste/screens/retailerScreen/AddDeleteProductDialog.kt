package com.example.zero_waste.screens.retailerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zero_waste.data.model.Product
import com.example.zero_waste.viewmodel.RetailerViewModel

@Composable
fun DeleteProductDialog(viewModel: RetailerViewModel, onDismiss: () -> Unit, productId: Product) {
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier.background(Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Are you sure u want to delete this product")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        viewModel.deleteProduct(productId)
                        onDismiss()
                    }) {
                        Text("Delete")
                    }
                    Button(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }

                }
            }
        }
    }
}