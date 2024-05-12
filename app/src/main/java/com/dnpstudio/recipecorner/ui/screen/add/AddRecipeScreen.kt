package com.dnpstudio.recipecorner.ui.screen.add

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.preference.LocalUser
import com.dnpstudio.recipecorner.ui.screen.auth.login.LoginScreen
import com.dnpstudio.recipecorner.ui.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navigator: DestinationsNavigator,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {

    val addRecipeState by viewModel.addRecipeState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tambah Resep",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0xFF8C6A5D)
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.navigate(HomeScreenDestination()) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {

        var recipeImg by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var recipeName by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var ingredients by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var steps by remember {
            mutableStateOf(TextFieldValue(""))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {

            item {
                Column{
                    Card(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Gray)
                    ) {
                        AsyncImage(
                            model = null,
                            contentDescription = ""
                        )
                    }

                    //Kolom untuk mengisi nama resep
                    OutlinedTextField(
                        value = recipeName,
                        label = { Text(text = "Nama Resep") },
                        onValueChange = {
                            recipeName = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    //Kolom untuk mengisi bahan-bahan
                    OutlinedTextField(
                        value = ingredients,
                        label = { Text(text = "Bahan-bahan:") },
                        onValueChange = {
                            ingredients = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    //Kolom untuk mengisi langkah pembuatan
                    OutlinedTextField(
                        value = steps,
                        label = { Text(text = "Langkah pembuatan:") },
                        onValueChange = {
                            steps = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 24.dp)
                    ) {
                        //Tombol untuk konfirmasi penambahan resep
                        Button(
                            onClick = {
                                viewModel.addRecipe(
                                    Recipe(
                                        recipeName = recipeName.text,
                                        ingredients = ingredients.text,
                                        steps = steps.text
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Bottom),
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFF8C6A5D)
                            )
                        ) {
                            Text(text = "Konfirmasi")
                        }
                    }

                }

                addRecipeState.DisplayResult(
                    onLoading = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    onSuccess = {
                        navigator.navigate(HomeScreenDestination)
                        Toast.makeText(context, "Sukses menambahkan resep", Toast.LENGTH_SHORT)
                            .show()
                    },
                    onError = { it, _ ->
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        Log.d("OSAS", it)
                    }
                )
            }
        }

    }
}
