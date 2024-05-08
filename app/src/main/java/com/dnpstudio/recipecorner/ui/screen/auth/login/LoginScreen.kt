package com.dnpstudio.recipecorner.ui.screen.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 72.dp, horizontal = 16.dp)
    ) {

        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        //Judul halaman Register
        Text(
            text = "Masuk",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
        )
        
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Mulai dengan menyambungkan akun anda",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 180.dp)
        ) {
            //SUSUNAN CARD UNTUK MENGISI EMAIL DAN PASSWORD
            OutlinedTextField(
                value = "",
                label = { Text(text = "Email")},
                onValueChange = {
                    email = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = "",
                label = { Text(text = "Kata Sandi")},
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Belum punya akun?")
                TextButton(onClick = {}) {
                    Text(text = "Daftar")
                }
            }
        }

        //BUTTON UNTUK MENYELESAIKAN LOGIN
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF8C6A5D)
                ),
                onClick = {}
            ) {
                Text(text = "Konfirmasi")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}