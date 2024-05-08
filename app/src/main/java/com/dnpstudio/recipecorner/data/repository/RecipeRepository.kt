package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

   suspend fun getRecipe(): Result<Flow<List<Recipe>>>

   suspend fun getRecipeDetail(): Result<Flow<Recipe>>

//   suspend fun insertRecipe(): Recipe
//
//   suspend fun updateRecipe(): Recipe

   suspend fun deleteRecipe(
      id: Int
   ): Recipe

   suspend fun register(username: String, email: String, password: String)

//   suspend fun login(email: String, password: String)

   suspend fun unsubcribeChannel()

}