package com.dnpstudio.recipecorner.ui.screen.profile

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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dnpstudio.recipecorner.preference.Preferences
import com.dnpstudio.recipecorner.ui.item.FavoriteRecipeItem
import com.dnpstudio.recipecorner.ui.screen.destinations.DetailScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.EditProfileScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.LoginScreenDestination
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.dnpstudio.recipecorner.utils.GlobalState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    var checked by remember {
        mutableStateOf(GlobalState.isDarkMode)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val favoriteList = viewModel.favoriteState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
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
                                navigator.popBackStack()
                            }
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dark Mode",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                GlobalState.isDarkMode = it
                                Preferences.isDarkMode = it
                            }
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
                .background(MaterialTheme.colorScheme.background),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = Preferences.username ?: "",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Button(
                            onClick = {
                                navigator.navigate(EditProfileScreenDestination(
                                    ProfileArguments(
                                        id = Preferences.id ?: "",
                                        username = Preferences.username ?: ""
                                    )
                                ))
                            },
                            colors = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Edit Profil",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Button(
                        onClick = { showAlertDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Keluar",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    if (showAlertDialog){
                        AlertDialog(
                            title = { Text(text = "Keluar")},
                            text = { Text(text = "Apakah anda ingin keluar dari akun yang sekarang?")},
                            onDismissRequest = {showAlertDialog = false},
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        Preferences.id = null
                                        Preferences.username = null
                                        navigator.navigate(LoginScreenDestination){
                                            launchSingleTop = true
                                        }
                                    }
                                ) {
                                    Text(text = "Iya")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showAlertDialog = false
                                    }
                                ) {
                                    Text(text = "Tidak")
                                }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
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
                Text(
                    text = "Resep Favorit",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(vertical = 16.dp)
                )
                if (favoriteList.isEmpty()){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Belum ada resep favorit",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                } else  {
                    LazyColumn {
                        items(favoriteList.size) { count ->
                            val favorite = favoriteList.get(count)
                            FavoriteRecipeItem(
                                favRecipeName = favorite.favRecipeName,
                                favRecipeImg = favorite.favRecipeImg.toString(),
                                onClick = {
                                    navigator.navigate(
                                        DetailScreenDestination(
                                            navArgs = DetailArguments(
                                                id = favorite.id,
                                                recipeName = favorite.favRecipeName,
                                                recipeHolder = favorite.favRecipeHolder,
                                                recipeImg = favorite.favRecipeImg.toString(),
                                                ingredients = favorite.favIngredients,
                                                steps = favorite.favSteps
                                            )
                                        )
                                    )
                                }
                            ) {
                                viewModel.deleteFavorite(
                                    favorite
                                )
                                Toast.makeText(
                                    context,
                                    "Berhasil menghapus resep favorit",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }
            }
        }
    }
}

data class  ProfileArguments(
    val id: String,
    val username: String
)