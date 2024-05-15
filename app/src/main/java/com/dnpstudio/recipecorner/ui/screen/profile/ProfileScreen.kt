package com.dnpstudio.recipecorner.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.dnpstudio.recipecorner.preference.KotPref
import com.dnpstudio.recipecorner.ui.item.FavoriteRecipeItem
import com.dnpstudio.recipecorner.ui.screen.destinations.DetailScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.EditProfileScreenDestination
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
        mutableStateOf(true)
    }

    val favoriteList = viewModel.favoriteState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.background,
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
                            color = MaterialTheme.colorScheme.background
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                GlobalState.isDarkMode = it
                                KotPref.isDarkMode = it
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
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .padding(start = 12.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                Column{
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Username",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Button(
                        onClick = {navigator.navigate(EditProfileScreenDestination)},
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Edit Profil",
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(shape = RoundedCornerShape(5.dp)),
                color = MaterialTheme.colorScheme.primary,
                thickness = 6.dp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Resep Favorit",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn{
                items(favoriteList.size){ count ->

                    val favorite = favoriteList.get(count)

                    FavoriteRecipeItem(
                        favRecipeName = favorite.favRecipeName,
                        onClick = {
                            navigator.navigate(
                                DetailScreenDestination(
                                    navArgs =  DetailArguments(
                                        id = favorite.id,
                                        recipeName = favorite.favRecipeName,
                                        ingredients = favorite.favIngredients,
                                        steps = favorite.favSteps
                                    )
                                )
                            )
                        },
                        onDelete = {
                            viewModel.deleteFavorite(
                                favorite
                            )
                            Toast.makeText(context, "Berhasil menghapus resep favorit", Toast.LENGTH_SHORT).show()
                        }
                    )

                }
            }

        }
    }
}