package com.dnpstudio.recipecorner.ui.screen.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dnpstudio.recipecorner.R
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import com.dnpstudio.recipecorner.preference.Preferences
import com.dnpstudio.recipecorner.ui.screen.destinations.UpdateRecipeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailArguments::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val detailState = viewModel.detailState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val favoriteState = viewModel.favoriteState

    val snackBarScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(true) {
        if (!viewModel.navArgs.isFromFavorite){
            viewModel.onRealtimeRecipeDetail(viewModel.navArgs.id!!)
        } else {
            viewModel.getFavoriteData()
            snackBarScope.launch {
                snackBarHostState.showSnackbar(message = "Ini data lama dari favorit")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                navigator.navigateUp()
                            }
                    )
                },
                actions = {
                    Spacer(modifier = Modifier.size(12.dp))
                    IconButton(onClick = {
                        if (viewModel.navArgs.recipeHolder == Preferences.id){
                            navigator.navigate(
                                UpdateRecipeScreenDestination(
                                    DetailArguments(
                                        id = detailState.value.getSuccessData().id,
                                        recipeName = detailState.value.getSuccessData().recipeName,
                                        recipeHolder = detailState.value.getSuccessData().recipeHolder,
                                        recipeImg = detailState.value.getSuccessData().recipeImg,
                                        ingredients = detailState.value.getSuccessData().ingredients,
                                        steps = detailState.value.getSuccessData().steps
                                    )
                                )
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            detailState.value.DisplayResult(
                onLoading = {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                },
                onSuccess = { detailView ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                            AsyncImage(
                                model = detailView.recipeImg,
                                contentDescription = "",
                                fallback = painterResource(id = R.drawable.no_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(shape = RoundedCornerShape(15.dp))
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = detailView.recipeName,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                IconButton(onClick = {
                                    viewModel.insertFavorite(
                                        Favorite(
                                            id = detailState.value.getSuccessData().id,
                                            favRecipeName = detailState.value.getSuccessData().recipeName,
                                            favRecipeImg = detailState.value.getSuccessData().recipeImg,
                                            favRecipeHolder = detailState.value.getSuccessData().recipeHolder ?: "",
                                            favIngredients = detailState.value.getSuccessData().ingredients,
                                            favSteps = detailState.value.getSuccessData().steps
                                        )
                                    )
                                    Toast.makeText(context, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
//                                IconToggleButton(
//                                    checked = favoriteState,
//                                    onCheckedChange = {
//                                        when(!favoriteState){
//                                            true -> {
//                                                viewModel.insertFavorite(
//                                                    Favorite(
//                                                        id = detailState.value.getSuccessData().id,
//                                                        favRecipeName = detailState.value.getSuccessData().recipeName,
//                                                        favRecipeHolder = detailState.value.getSuccessData().recipeHolder?: "",
//                                                        favRecipeImg = detailState.value.getSuccessData().recipeImg,
//                                                        favIngredients = detailState.value.getSuccessData().ingredients,
//                                                        favSteps = detailState.value.getSuccessData().steps
//                                                    )
//                                                )
//                                                Toast.makeText(context, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
//                                            }
//                                            false -> {
//                                                viewModel.deleteFavorite(
//                                                    Favorite(
//                                                        id = detailState.value.getSuccessData().id,
//                                                        favRecipeName = detailState.value.getSuccessData().recipeName,
//                                                        favRecipeImg = detailState.value.getSuccessData().recipeImg,
//                                                        favRecipeHolder = detailState.value.getSuccessData().recipeHolder?: "",
//                                                        favIngredients = detailState.value.getSuccessData().ingredients,
//                                                        favSteps = detailState.value.getSuccessData().steps
//                                                    )
//                                                )
//                                                Toast.makeText(context, "Berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show()
//                                            }
//                                        }
//                                    },
//
//                                ) {
//                                    Icon(
//                                        imageVector = when (favoriteState) {
//                                            true -> {
//                                                Icons.Default.Favorite
//                                            }
//                                            false -> {
//                                                Icons.Default.FavoriteBorder
//                                            }
//                                        },
//                                        contentDescription = "",
//                                        tint = MaterialTheme.colorScheme.primary
//                                    )
//                                }
                            }
                            //Bagian Bahan-bahan (Ingredients)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Bahan-bahan:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = detailView.ingredients,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            //Bagian Langkah Pembuatan (Steps)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Langkah Pembuatan:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = detailView.steps,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                },
                onError = { it, _ ->
                    Toast.makeText(context, "Eror ketika menampilkan detail", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("DETAIL", it)
                }
            )
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.leaveRealtimeChannel()
        }
    }
}

