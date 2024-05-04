package com.dnpstudio.recipecorner.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dnpstudio.recipecorner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailRecipeName: String,
    detailRecipeHolder: String,
    ingredients: String,
    steps: String
) {
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
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
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
                    text = detailRecipeName,
                    fontSize = 24.sp
                )
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = detailRecipeHolder,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Bahan-bahan:")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = ingredients)

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Langkah Pembuatan:")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = steps)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        detailRecipeName = "Nama Resep",
        detailRecipeHolder = "Pemegang resep",
        ingredients = "Bahan...",
        steps = "Langkah.."
    )
}