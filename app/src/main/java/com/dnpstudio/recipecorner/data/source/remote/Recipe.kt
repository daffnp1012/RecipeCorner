package com.dnpstudio.recipecorner.data.source.remote

import androidx.room.PrimaryKey
import com.chibatching.kotpref.Kotpref
import com.dnpstudio.recipecorner.preference.Preferences
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id") val id: Int? = null,
    @SerialName("recipe_holder") val recipeHolder: String = Preferences.id ?: "",
    @SerialName("recipe_name") val recipeName: String,
    @SerialName("recipe_img") val recipeImg: String,
    @SerialName("ingredients") val ingredients: String,
    @SerialName("steps") val steps: String
)
