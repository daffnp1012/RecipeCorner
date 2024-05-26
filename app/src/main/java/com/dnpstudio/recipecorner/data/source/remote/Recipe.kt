package com.dnpstudio.recipecorner.data.source.remote

import com.dnpstudio.recipecorner.preference.Preferences
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("recipe_holder") val recipeHolder: String?,
    @SerialName("recipe_name") val recipeName: String,
    @SerialName("ingredients") val ingredients: String,
    @SerialName("steps") val steps: String,
    @SerialName("recipe_img") val recipeImg: String?,
    @SerialName("id") val id: Int? = null
)
