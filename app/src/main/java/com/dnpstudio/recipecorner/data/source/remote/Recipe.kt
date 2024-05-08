package com.dnpstudio.recipecorner.data.source.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("recipe_name") val recipeName: String,
    @SerialName("recipe_img") val recipeImg: String?,
    @SerialName("ingredients") val ingredients: String,
    @SerialName("steps") val steps: String
)
