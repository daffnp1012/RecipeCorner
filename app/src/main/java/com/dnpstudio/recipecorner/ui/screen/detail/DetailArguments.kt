package com.dnpstudio.recipecorner.ui.screen.detail

data class DetailArguments(
    val id: Int?,
    val recipeName: String?,
    val recipeHolder: String?,
    val recipeImg: String?,
    val ingredients: String?,
    val steps: String?,
    val isFromFavorite: Boolean = false
)
