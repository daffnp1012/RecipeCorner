package com.dnpstudio.recipecorner.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.ui.item.RecipeItem
import com.dnpstudio.recipecorner.ui.screen.destinations.AddRecipeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.DetailScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.ProfileScreenDestination
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val homeState = homeViewModel.recipeListState.collectAsStateWithLifecycle()

    val searchText by homeViewModel.searchText.collectAsState()
    val isSearching by homeViewModel.isSearching.collectAsState()
//    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.onRealtimeRecipeList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color(0xFF8C6A5D)
                ),
                actions = {
                    //Ikon tombol untuk menuju halaman tambah resep
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable { navigator.navigate(AddRecipeScreenDestination()) },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    //Ikon tombol menuju halaman Profile
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable { navigator.navigate(ProfileScreenDestination()) }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                },
            )

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                homeState.value.DisplayResult(
                    onLoading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    onSuccess = { recipeList ->

                        if (recipeList.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center)
                            ) {
                                Text(text = "Belum ada resep :(")
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {

                                SearchBar(
                                    query = searchText,
                                    onQueryChange = homeViewModel::onSearchTextChange,
                                    onSearch = homeViewModel::onSearchTextChange,
                                    active = isSearching,
                                    onActiveChange = { homeViewModel.onToogleSearch() },
                                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                                    placeholder = { Text(text = "Cari") },
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 12.dp)
                                ) {

                                }

                                Text(
                                    text = "Halo! Pengguna",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(
                                            top = 12.dp,
                                            start = 16.dp
                                        )
                                )

                                Spacer(modifier = Modifier.size(16.dp))

                                Text(
                                    text = "Resep Anda",
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp
                                        )
                                )

                                Spacer(modifier = Modifier.size(16.dp))

                                LazyColumn{
                                    items(recipeList) { recipe ->

                                        RecipeItem(
                                            recipeName = recipe.recipeName,
                                            onClick = {
                                                navigator.navigate(
                                                    DetailScreenDestination(
                                                        DetailArguments(
                                                            id = recipe.id,
                                                            recipeName = recipe.recipeName,
                                                            ingredients = recipe.ingredients,
                                                            steps = recipe.steps
                                                        )
                                                    )
                                                )
                                            },
                                            onDelete = {
                                                homeViewModel.deleteRecipe(recipe.id!!)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    onError = { _, _ ->

                    }
                )

            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { homeViewModel.leaveRealtimeChannel() }
    }

}