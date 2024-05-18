package com.dnpstudio.recipecorner.data.repository

import android.net.Uri
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.rmaprojects.apirequeststate.ResponseState
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipe(): Result<Flow<List<Recipe>>>

    suspend fun getRecipeDetail(recipeId: Int): Result<Flow<Recipe>>

    fun insertRecipe(
        recipe: Recipe
    ): Flow<ResponseState<Recipe>>

    fun updateRecipe(
        recipe: Recipe
    ): Flow<ResponseState<Boolean>>

    fun deleteRecipe(id: Int): Flow<ResponseState<List<Recipe>>>

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Flow<ResponseState<Boolean>>

   suspend fun login(
       email: String,
       password: String
   ): Flow<ResponseState<Boolean>>

    suspend fun unsubcribeChannel()

    fun getFavoriteList(): Flow<List<Favorite>>

    suspend fun insertFavorite(favorite: Favorite)

    suspend fun deleteFavorite(favorite: Favorite)

    suspend fun uploadFile(recipeName: String, file: Uri): String

    suspend fun editProfile(id: String, username: String): Flow<ResponseState<Boolean>>


}