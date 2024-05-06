package com.dnpstudio.recipecorner.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dnpstudio.recipecorner.ui.item.RecipeItem
import com.dnpstudio.recipecorner.ui.screen.destinations.AddRecipeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.ProfileScreenDestination
import com.dnpstudio.recipecorner.ui.screen.home.event.HomeEvent
import com.dnpstudio.recipecorner.ui.screen.home.state.HomeState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val homeState by homeViewModel.recipeListState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true){
        homeViewModel.onEvent(HomeEvent.getRecipe)
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

            Box(modifier = Modifier.fillMaxSize()){

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

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Resep Anda",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                homeState.let { state ->
                    when(state){
                        //Ketika terjadi eror
                        is HomeState.Error -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }
                        //Ketika sedang  memuat data
                        HomeState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        //Ketika sukses menampilkan data
                        is HomeState.Success -> {
                            //List penampil item resep
                            LazyColumn {
                                items(state.list){ recipe ->
                                    RecipeItem(
                                        recipeName = recipe.recipeName
                                    )
                                }
                            }
                        }
                        //ketika tidak ada/ditemukan konten untuk ditampilkan
                        null -> {
                            Text(
                                text = "Tidak ada konten",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        else -> {

                        }
                    }
                }

            }
        }
    }
}