package com.example.zero_waste.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.zero_waste.R

@Composable
fun Rectangle(screenWidth: Dp , imageWidthFraction:Float)
{
    Image(
        painter = painterResource(R.drawable.rectangleframe),
        contentDescription = "rectangle",
        modifier = Modifier
            .width(screenWidth * imageWidthFraction)
            .aspectRatio(3f), // 936 / 312 = 3
        contentScale = ContentScale.Fit
    )
}