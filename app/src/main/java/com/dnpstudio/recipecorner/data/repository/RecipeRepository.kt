package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe

interface RecipeRepository {

   suspend fun getRecipe(): List<Recipe>

   suspend fun getRecipeDetail(): Recipe

}