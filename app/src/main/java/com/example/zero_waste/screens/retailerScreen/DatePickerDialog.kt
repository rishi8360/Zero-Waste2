package com.example.zero_waste.screens.retailerScreen

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun DatePickerField(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
    ) {


    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateSelected(String.format(Locale.US,"%02d/%02d/%04d", day, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // Call onDismiss when the dialog is cancelled
            setOnCancelListener { onDismiss() }
        }
    }
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }

}

