package com.dnpstudio.recipecorner.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.preference.Preferences
import com.dnpstudio.recipecorner.ui.item.RecipeItem
import com.dnpstudio.recipecorner.ui.item.SearchRecipeItem
import com.dnpstudio.recipecorner.ui.screen.destinations.AddRecipeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.DetailScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.ProfileScreenDestination
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState =
        viewModel.recipeListState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    var isRefreshing = viewModel.isRefreshing
    val context = LocalContext.current
    val refreshState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.onRealtimeRecipeList()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RecipeCorner",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.background
                ),
                actions = {
                    //Ikon tombol untuk menuju halaman tambah resep
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                viewModel.refresh()
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    //Ikon tombol untuk menuju halaman tambah resep
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable { navigator.navigate(AddRecipeScreenDestination()) },
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    //Ikon tombol menuju halaman Profile
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(refreshState.nestedScrollConnection)
            ) {
                homeState.value.DisplayResult(
                    //Ketika loading...
                    onLoading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    //Ketika sukses...
                    onSuccess = { recipeList ->
                        //Apabila belum ada resep...
                        if (recipeList.isEmpty()) {
                            Column {
                                Text(
                                    text = "Halo! ${Preferences.username ?: ""}",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .padding(
                                            top = 16.dp,
                                            start = 16.dp
                                        )
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Belum ada resep :(",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                TextButton(onClick = {
                                    navigator.navigate(AddRecipeScreenDestination)
                                }) {
                                    Text(
                                        text = "Tambah resep?",
                                        color = MaterialTheme.colorScheme.tertiary,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                            //Jika sudah ada resep...
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                //SearchBar untuk mencari resep...
                                SearchBar(
                                    query = searchText,
                                    onQueryChange = viewModel::onSearchTextChange,
                                    onSearch = viewModel::onSearchTextChange,
                                    active = isSearching,
                                    onActiveChange = { viewModel.onToogleSearch() },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Cari",
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 12.dp),
                                    colors = SearchBarDefaults.colors(
                                        MaterialTheme.colorScheme.background
                                    ),
                                    shadowElevation = 8.dp
                                ) {
                                    Column {
                                        //Hasil tempilan
                                        homeState.value.DisplayResult(
                                            //Ketika Loading...
                                            onLoading = {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            },
                                            //Ketika sukses...
                                            onSuccess = {
                                                LazyColumn {
                                                    items(it.filter {
                                                        it.recipeName.contains(
                                                            searchText,
                                                            true
                                                        )
                                                    }) { recipe ->
                                                        SearchRecipeItem(
                                                            recipeName = recipe.recipeName,
                                                            recipeImg = recipe.recipeImg.toString()
                                                        ) {
                                                            navigator.navigate(
                                                                DetailScreenDestination(
                                                                    navArgs = DetailArguments(
                                                                        id = recipe.id,
                                                                        recipeName = recipe.recipeName,
                                                                        recipeHolder = recipe.recipeHolder,
                                                                        recipeImg = recipe.recipeImg.toString(),
                                                                        ingredients = recipe.ingredients,
                                                                        steps = recipe.steps,
                                                                    )
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                            },
                                            //Ketika terjadi eror...
                                            onError = { it, _ ->
                                                Toast.makeText(
                                                    context,
                                                    "Terjadi kesalahan saat memuat resep",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                                }
                                Text(
                                    text = "Halo! ${Preferences.username ?: ""}",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .padding(
                                            top = 16.dp,
                                            start = 16.dp
                                        )
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Resep Anda",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp
                                        )
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(
                                            shape = RoundedCornerShape(
                                                topStart = 15.dp,
                                                topEnd = 15.dp
                                            )
                                        )
                                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                                ) {
                                    LazyColumn {
                                        items(recipeList) { recipe ->
                                            RecipeItem(
                                                recipeName = recipe.recipeName,
                                                recipeHolder = recipe.recipeHolder ?: "",
                                                recipeImg = recipe.recipeImg.toString(),
                                                onClick = {
                                                    navigator.navigate(
                                                        DetailScreenDestination(
                                                            DetailArguments(
                                                                id = recipe.id,
                                                                recipeName = recipe.recipeName,
                                                                recipeImg = recipe.recipeImg,
                                                                recipeHolder = recipe.recipeHolder,
                                                                ingredients = recipe.ingredients,
                                                                steps = recipe.steps
                                                            )
                                                        )
                                                    )
                                                }
                                            ) {
                                                viewModel.deleteRecipe(recipe.id!!)
                                                Toast.makeText(
                                                    context,
                                                    "Berhasil menghapus resep",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    onError = { _, _ ->
                        Toast.makeText(context, "Gagal memuat resep", Toast.LENGTH_SHORT).show()
                        return@DisplayResult
                    }
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.leaveRealtimeChannel() }
    }

}