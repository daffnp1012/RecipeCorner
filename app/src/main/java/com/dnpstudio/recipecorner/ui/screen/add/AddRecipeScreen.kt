package com.dnpstudio.recipecorner.ui.screen.add

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.preference.Preferences
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
    BackHandler(addRecipeState.isLoading()) {}
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tambah Resep",
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!addRecipeState.isLoading()){
                                navigator.navigateUp()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            )
        }
    ) {
        var recipeName by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var ingredients by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var steps by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val singlePhotoClicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                selectedImageUri = it
            }
        )
        Spacer(modifier = Modifier.size(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn{
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        onClick = {
                            singlePhotoClicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    //Kolom untuk mengisi nama resep
                    OutlinedTextField(
                        value = recipeName,
                        label = {
                            Text(
                                text = "Nama Resep",
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                        onValueChange = {
                            recipeName = it
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
                    Spacer(modifier = Modifier.height(16.dp))
                    //Kolom untuk mengisi bahan-bahan
                    OutlinedTextField(
                        value = ingredients,
                        label = {
                            Text(
                                text = "Bahan-bahan:",
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                        onValueChange = {
                            ingredients = it
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
                    Spacer(modifier = Modifier.height(16.dp))
                    //Kolom untuk mengisi langkah pembuatan
                    OutlinedTextField(
                        value = steps,
                        label = {
                            Text(
                                text = "Langkah pembuatan:",
                                color = MaterialTheme.colorScheme.background
                            )
                        },
                        onValueChange = {
                            steps = it
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
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 24.dp)
                    ) {
                        //Tombol untuk konfirmasi penambahan resep
                        Button(
                            onClick = {
                                viewModel.addRecipe(
                                    recipe =  Recipe(
                                        recipeHolder = Preferences.id ?: "",
                                        recipeName = recipeName.text,
                                        ingredients = ingredients.text,
                                        steps = steps.text,
                                        recipeImg = selectedImageUri.toString(),
                                        id = null
                                    ),
                                   file = selectedImageUri
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .align(Alignment.Bottom),
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
                    addRecipeState.DisplayResult(
                        onLoading = {
                            CircularProgressIndicator()
                        },
                        onSuccess = {
                            navigator.navigateUp()
                            Toast.makeText(context, "Berhasil menambahkan resep", Toast.LENGTH_SHORT).show()
                        },
                        onError = { it, _ ->
                            Toast.makeText(context, "Gagal menambahkan resep", Toast.LENGTH_SHORT).show()
                            Log.d("ADD", it)
                        }
                    )
                }
            }
        }
    }
}

