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
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.*
import com.example.zero_waste.screens.components.Rectangle
import com.example.zero_waste.ui.theme.GreenGradientBrush
import com.example.zero_waste.ui.theme.LightZeroWasteColorScheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    navController: NavHostController, // Add this
    onLoginSuccess: (String) -> Unit
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_medium)
    )
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("retailer") }

    val context = LocalContext.current

    // Root layout with access to screen dimensions
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val screenWidth = maxWidth

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Background image + heading
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
                        "Sign in ",
                        fontSize = 40.sp,
                        fontFamily = rubikFontFamily,
                        color = Color(0xFF424242),
                        modifier = Modifier.padding(bottom = 20.dp, start = 5.dp)
                    )
                    // Green underline below heading
                    Line(95.dp, 1f)
                }
            }

            // Email input field
            CustomEmailInputField(email, onEmailChange = { input ->
                email = input.filter { it != ' ' }
            })

            Spacer(modifier = Modifier.height(24.dp))

            // Password input field
            CustomPasswordInputField(password, onPasswordChange = { input ->
                password = input.filter { it != ' ' }
            })

            // Role selector (Retailer / Customer)
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                val selectedColor = Color(0xFF59ca67) // Green for selected
                val unselectedColor = Color.Gray

                listOf("retailer", "customer").forEach { role ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { selectedRole = role }
                    ) {
                        // Square radio indicator
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

            // Login button (clickable Box that uses a custom Rectangle composable)

            Button(
                onClick = {
                    if (!email.isEmpty() && !password.isEmpty()) {
                        viewModel.login(email, password, selectedRole)
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill email id and password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).padding(start = 4.dp, end = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors( LightZeroWasteColorScheme.primary)
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
            Text(
                text = "Don't have an account? Sign up",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        navController.navigate("signup")
                    }
            )



            // Login result handling
            when (viewModel.loginState) {
                true -> onLoginSuccess(selectedRole)
                false -> Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                null -> {}
            }
        }
    }
}

@Composable
fun CustomEmailInputField(
    email: String,
    onEmailChange: (String) -> Unit
) {
    val rubikRegularFontFamily = FontFamily(
        Font(R.font.rubik_regular)
    )
    val rubikMediumFontFamily = FontFamily(
        Font(R.font.rubik_medium)
    )

    Column(modifier = Modifier.padding(horizontal = 25.dp)) {

        // Label
        Text(
            text = "Email",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = rubikMediumFontFamily,
            color = Color(0xFF616161)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email input row with icon and text field
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Email icon
            Icon(
                imageVector = Lucide.Mail,
                contentDescription = "Email Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // BasicTextField for email input
            BasicTextField(
                value = email,
                onValueChange = onEmailChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                decorationBox = { innerTextField ->
                    if (email.isEmpty()) {
                        Text(
                            text = "demo@email.com",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontFamily = rubikRegularFontFamily
                        )
                    }
                    innerTextField()
                }
            )
        }

        // Green underline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF59ca67).copy(alpha = 0.6f))
        )
    }
}

@Composable
fun CustomPasswordInputField(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 25.dp)) {

        // Label
        Text(
            text = "Password",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color(0xFF616161)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input row with icon and text field
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Password icon
            Icon(
                imageVector = Lucide.Key,
                contentDescription = "Password Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // BasicTextField for password input
            BasicTextField(
                value = password,
                onValueChange = onPasswordChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (password.isEmpty()) {
                        Text(
                            text = "Enter your password",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }

        // Green underline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GreenGradientBrush)
        )
    }
}


