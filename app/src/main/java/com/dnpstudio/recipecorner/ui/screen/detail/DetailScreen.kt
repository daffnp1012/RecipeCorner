package com.dnpstudio.recipecorner.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.R
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import com.dnpstudio.recipecorner.ui.screen.destinations.UpdateRecipeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailArguments::class)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val detailState = detailViewModel.detailState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(true) {
        detailViewModel.onRealtimeRecipeDetail(detailViewModel.navArgs.id!!)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0xFF8C6A5D)
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                navigator.popBackStack()
                            }
                    )
                },
                actions = {
                    Spacer(modifier = Modifier.size(12.dp))
                    IconButton(onClick = {
                        navigator.navigate(UpdateRecipeScreenDestination(
                            DetailArguments(
                                id = detailState.value.getSuccessData().id,
                                recipeName = detailState.value.getSuccessData().recipeName,
                                ingredients = detailState.value.getSuccessData().ingredients,
                                steps = detailState.value.getSuccessData().steps
                            )
                        ))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
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
                            Image(
                                painter = painterResource(id = R.drawable.food_img_1),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .height(240.dp)
                                    .clip(shape = RoundedCornerShape(15.dp))
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = detailView.recipeName,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(onClick = {
                                    detailViewModel.insertFavorite(
                                        Favorite(
                                            id = null,
                                            favRecipeName = detailView.recipeName
                                        )
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = ""
                                    )
                                }
                            }

                            //Bagian Bahan-bahan (Ingredients)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Bahan-bahan:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = detailView.ingredients,
                                fontSize = 15.sp
                            )

                            //Bagian Langkah Pembuatan (Steps)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Langkah Pembuatan:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = detailView.steps,
                                fontSize = 15.sp
                            )
                        }
                    }
                },
                onError = { _, _ ->
                    Toast.makeText(context, "Eror ketika menampilkan detail", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            detailViewModel.leaveRealtimeChannel()
        }
    }
}

