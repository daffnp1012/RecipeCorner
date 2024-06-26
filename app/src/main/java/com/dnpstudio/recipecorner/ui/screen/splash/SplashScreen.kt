package com.dnpstudio.recipecorner.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.dnpstudio.recipecorner.R
import com.dnpstudio.recipecorner.ui.screen.destinations.HomeScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.RegisterScreenDestination
import com.dnpstudio.recipecorner.ui.screen.destinations.SplashScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.delay

@Destination
@RootNavGraph(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    LaunchedEffect(true){
        delay(2500)
        navigator.navigate(RegisterScreenDestination){
            popUpTo(SplashScreenDestination){
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "",
        )
    }

}
