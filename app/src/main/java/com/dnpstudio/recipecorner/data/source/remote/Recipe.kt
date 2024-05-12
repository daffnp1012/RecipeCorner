package com.dnpstudio.recipecorner.data.source.remote

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id") val id: Int? = null,
    @SerialName("recipe_name") val recipeName: String,
    @SerialName("recipe_img") val recipeImg: String? = null,
    @SerialName("ingredients") val ingredients: String,
    @SerialName("steps") val steps: String
)
