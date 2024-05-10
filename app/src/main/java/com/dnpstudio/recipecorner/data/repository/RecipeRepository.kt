package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import com.rmaprojects.apirequeststate.ResponseState
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipe(): Result<Flow<List<Recipe>>>

    suspend fun getRecipeDetail(): Result<Flow<Recipe>>

    fun insertRecipe(
        recipe: Recipe
    ): Flow<ResponseState<Recipe>>

    //
//    fun updateRecipe(
//        recipeName: String,
//        recipeImg: String,
//        ingredients: String,
//        steps: String
//    ): Flow<ResponseState<Recipe>>

    fun deleteRecipe(id: Int): Flow<ResponseState<List<Recipe>>>

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Flow<ResponseState<Boolean>>

//   suspend fun login(email: String, password: String)

    suspend fun unsubcribeChannel()

}