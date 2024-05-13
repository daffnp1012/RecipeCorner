package com.dnpstudio.recipecorner.ui.screen.update

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailArguments::class)
@Composable
fun UpdateRecipeScreen(
    navigator: DestinationsNavigator,
    viewModel: UpdateRecipeViewModel = hiltViewModel()
) {

    var updateRecipeName by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.recipeName))
    }

    var updateIngredients by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.ingredients))
    }

    var updateSteps by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.steps))
    }

    val context = LocalContext.current
    val updateRecipeState = viewModel.updateRecipeState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Update Resep",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0xFF8C6A5D)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {navigator.popBackStack()},
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            LazyColumn{
                item {
                    OutlinedTextField(
                        value = updateRecipeName,
                        label = { Text(text = "Nama Resep") },
                        onValueChange = {
                            updateRecipeName = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = updateIngredients,
                        label = { Text(text = "Bahan-bahan:") },
                        onValueChange = {
                            updateIngredients = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = updateSteps,
                        label = { Text(text = "Langkah pembuatan:") },
                        onValueChange = {
                            updateSteps = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 24.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.updateRecipe(
                                    id = viewModel.navArgs.id!!,
                                    recipeImg = "",
                                    recipeName = updateRecipeName.text,
                                    ingredients = updateIngredients.text,
                                    steps = updateSteps.text
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
                        updateRecipeState.value.DisplayResult(
                            onLoading = {
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                )
                            },
                            onSuccess = {
                                navigator.popBackStack()
                            },
                            onError = { _, _, ->
                                Toast.makeText(context, "Terjadi eror saat meng-update resep", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}