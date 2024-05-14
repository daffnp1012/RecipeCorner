package com.dnpstudio.recipecorner.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dnpstudio.recipecorner.R
import com.dnpstudio.recipecorner.ui.screen.profile.ProfileViewModel

@Composable
fun FavoriteRecipeItem(
    favId: Int,
    favRecipeName: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(72.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = favRecipeName,
                    color = MaterialTheme.colorScheme.background
                )
            }
            Image(
                painter = painterResource(id = R.drawable.food_img_1),
                contentDescription = ""
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FavoriteRecipeItemPreview() {
//    FavoriteRecipeItem(favId = 0, favRecipeName = "Nama Resep")
//}
