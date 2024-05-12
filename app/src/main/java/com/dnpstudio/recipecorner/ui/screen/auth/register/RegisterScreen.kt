package com.dnpstudio.recipecorner.ui.screen.auth.register

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.preference.LocalUser
import com.dnpstudio.recipecorner.ui.screen.destinations.HomeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.LoginScreenDestination
import com.dnpstudio.recipecorner.utils.emailChecked
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RegisterScreen(
    registerVM: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    LaunchedEffect(true){
        if (LocalUser.id.isNotEmpty()){
            navigator.navigate(LoginScreenDestination)
        }
    }

    val registerState = registerVM.registerState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var isEmailError by remember{ mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 16.dp)
    ) {

        var username by remember{(mutableStateOf(TextFieldValue("")))}
        var email by remember{(mutableStateOf(TextFieldValue("")))}
        var password by remember{(mutableStateOf(TextFieldValue("")))}

        //Judul halaman Register
        Text(
            text = "Daftar",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Buat akun anda untuk memulai",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
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
                label = { Text(text = "Username")},
                onValueChange = {
                    username = it
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                label = { Text(text = "Email")},
                onValueChange = {
                    email = it
                    isEmailError = it.text.emailChecked()
                },
                isError = isEmailError,
                supportingText = {
                    if (isEmailError){
                        Text(text = "Email tidak valid")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                label = { Text(text = "Kata Sandi")},
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sudah punya akun?")
                TextButton(onClick = {
                    navigator.navigate(LoginScreenDestination)
                }) {
                    Text(text = "Masuk")
                }
            }
        }
        
        Spacer(modifier = Modifier.size(48.dp))
        
        //BUTTON UNTUK MENYELESAIKAN PEMBUATAN AKUN
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = {
                    registerVM.register(username.text, email.text, password.text)
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF8C6A5D)
                )
            ){
                Text(text = "Konfirmasi")
            }
        }

        registerState.value.DisplayResult(
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp)
                )
            },
            onSuccess = {
                navigator.navigate(HomeScreenDestination)
            },
            onError = { it, _, ->
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                Log.d("LHEUGHA", it)
            }
        )

    }
}

//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen()
//}