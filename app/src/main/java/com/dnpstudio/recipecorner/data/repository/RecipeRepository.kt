package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import io.github.jan.supabase.gotrue.providers.builtin.Email

interface RecipeRepository {

   suspend fun getRecipe(): List<Recipe>

   suspend fun getRecipeDetail(): Recipe

//   suspend fun register(email: String, password: String): Email.Result?

//   suspend fun login(email: String, password: String)

}