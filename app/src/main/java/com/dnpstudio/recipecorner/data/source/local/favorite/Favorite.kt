package com.dnpstudio.recipecorner.data.source.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = false) val id: Int?,
    val favRecipeName: String,
    val favRecipeHolder: String,
    val favRecipeImg: String?,
    val favIngredients: String,
    val favSteps: String
)
