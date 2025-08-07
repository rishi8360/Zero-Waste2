package com.example.zero_waste.screens.login
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zero_waste.R
import com.example.zero_waste.screens.components.Line
import com.example.zero_waste.viewmodel.AuthViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.*
import com.example.zero_waste.screens.components.Rectangle
import com.example.zero_waste.ui.theme.GreenGradientBrush
import com.example.zero_waste.ui.theme.LightZeroWasteColorScheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SignUpScreen(viewModel: AuthViewModel, onSignUpSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("customer") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val loginState = viewModel.loginState

    // Observe login state
    LaunchedEffect(loginState) {
        if (loginState == true) {
            onSignUpSuccess()
        } else if (loginState == false) {
            Toast.makeText(context, "Signup failed. Try again.", Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }

    val rubikFontFamily = FontFamily(Font(R.font.rubik_medium))

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Background image and heading
            BoxWithConstraints {
                Image(
                    painter = painterResource(id = R.drawable.backgroundui),
                    contentDescription = "shapes",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
                        .padding(20.dp)
                        .padding(bottom = 40.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        "Sign Up",
                        fontSize = 40.sp,
                        fontFamily = rubikFontFamily,
                        color = Color(0xFF424242),
                        modifier = Modifier.padding(bottom = 20.dp, start = 5.dp)
                    )
                    Line(95.dp, 1f)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Styled email field (reused from login screen)
            CustomEmailInputField(email) {
                email = it.filter { char -> char != ' ' }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Styled password field
            CustomPasswordInputField(password) {
                password = it.filter { char -> char != ' ' }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Role selector
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                val selectedColor = Color(0xFF59ca67)
                val unselectedColor = Color.Gray

                listOf("retailer", "customer").forEach { role ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { selectedRole = role }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = if (selectedRole == role) selectedColor else unselectedColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = role.replaceFirstChar { it.uppercase() })
                    }
                }
            }

            // Sign-Up button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true
                        viewModel.signUp(email, password, selectedRole)
                    } else {
                        Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(start = 4.dp, end = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(LightZeroWasteColorScheme.primary)
            ) {
                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}
