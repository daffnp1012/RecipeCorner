package com.dnpstudio.recipecorner.ui.screen.auth.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.preference.Preferences
import com.dnpstudio.recipecorner.ui.screen.destinations.HomeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.LoginScreenDestination
import com.dnpstudio.recipecorner.utils.emailChecked
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    LaunchedEffect(true) {
        if (Preferences.id != null) {
            navigator.navigate(HomeScreenDestination)
        }
    }

    val registerState = viewModel.registerState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var isEmailError by remember { mutableStateOf(false) }

    var showPassword by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {

        //Nilai untuk textfield username
        var username by remember { (mutableStateOf(TextFieldValue(""))) }
        //Nilai untuk textfield email
        var email by remember { (mutableStateOf(TextFieldValue(""))) }
        //Nilai untuk textfield password
        var password by remember { (mutableStateOf(TextFieldValue(""))) }
        
        Spacer(modifier = Modifier.size(64.dp))

        //Judul halaman Register
        Text(
            text = "Daftar",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Buat akun anda untuk memulai",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            //Susunan card untuk mengisi email, password, dan konfirmasi password
            OutlinedTextField(
                value = username,
                label = {
                    Text(
                        text = "Username",
                        color = MaterialTheme.colorScheme.background
                    )
                },
                onValueChange = {
                    username = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                label = {
                    Text(
                        text = "Email",
                        color = MaterialTheme.colorScheme.background
                    )
                },
                onValueChange = {
                    email = it
                    isEmailError = it.text.emailChecked()
                },
                isError = isEmailError,
                supportingText = {
                    if (isEmailError) {
                        Text(
                            text = "Email tidak valid",
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    errorTextColor = MaterialTheme.colorScheme.error
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                label = {
                    Text(
                        text = "Kata Sandi",
                        color = MaterialTheme.colorScheme.background
                    )
                },
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    } else {
                        IconButton(onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sudah punya akun?",
                    color = MaterialTheme.colorScheme.background
                )
                TextButton(onClick = {
                    navigator.navigate(LoginScreenDestination)
                }) {
                    Text(
                        text = "Masuk",
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(64.dp))

        //BUTTON UNTUK MENYELESAIKAN PEMBUATAN AKUN
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = {
                    viewModel.register(username.text, email.text, password.text)
                },
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Konfirmasi",
                    color = MaterialTheme.colorScheme.background
                )
            }
        }

        registerState.value.DisplayResult(
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp)
                )
            },
            onSuccess = {
                navigator.navigate(HomeScreenDestination)
            },
            onError = { it, _ ->
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                Log.d("LHEUGHA", it)
            }
        )

    }
}