package com.dnpstudio.recipecorner.data.repository

import android.net.Uri
import android.util.Log
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import com.dnpstudio.recipecorner.data.source.local.favorite.FavoriteDatabase
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import com.dnpstudio.recipecorner.data.tables.SupabaseTables
import com.dnpstudio.recipecorner.preference.KotPref
import com.rmaprojects.apirequeststate.ResponseState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.realtime.postgresSingleDataFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    private val favoriteDatabase: FavoriteDatabase
) : RecipeRepository {

    private val recipeChannel = client.channel("recipe")

    override suspend fun unsubcribeChannel() {
        recipeChannel.unsubscribe()
        client.realtime.removeChannel(recipeChannel)
    }

    override fun getFavoriteList(): Flow<List<Favorite>> {
        return favoriteDatabase.favoriteDao().getFavoriteList()
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        return favoriteDatabase.favoriteDao().insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        return favoriteDatabase.favoriteDao().deleteFavorite(favorite)
    }

    override suspend fun uploadFile(recipeName: String, file: Uri): String {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipe(): Result<Flow<List<Recipe>>> {
        val data = recipeChannel.postgresListDataFlow(
            schema = "public",
            table = SupabaseTables.RECIPE_TABLE,
            primaryKey = Recipe::id,
        ).flowOn(Dispatchers.IO)

        return Result.success(data.map { it.sortedBy { it.id } })
    }

    override suspend fun getRecipeDetail(recipeId: Int): Result<Flow<Recipe>> {
        val data = recipeChannel.postgresSingleDataFlow(
            schema = "public",
            table = SupabaseTables.RECIPE_TABLE,
            primaryKey = Recipe::id
        ) {
            Recipe::id eq recipeId
        }

        return Result.success(data)
    }

    override fun deleteRecipe(
        id: Int
    ): Flow<ResponseState<List<Recipe>>> {

        return flow {
            emit(ResponseState.Loading)
            try {
                emit(
                    ResponseState.Success(
                        client.from("recipe").delete {
                            filter {
                                eq("id", id)
                            }
                        }.decodeList<Recipe>()
                    )
                )
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
                Log.d("DELETE", e.toString())
            }
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Flow<ResponseState<Boolean>> = flow {

        emit(ResponseState.Loading)
        try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("username", username)
                }
            }

            val user = client.auth.currentUserOrNull()
            val publicUser = client.from("users")
                .select {
                    filter {
                        User::id eq user?.id
                    }
                }.decodeSingle<User>()
            KotPref.apply {
                this.id = publicUser.id.toString()
                this.username = publicUser.username
                this.email = publicUser.email
            }
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.toString()))
        }

    }

    override suspend fun login(email: String, password: String): Flow<ResponseState<Boolean>> = flow {

        emit(ResponseState.Loading)
        try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val user = client.auth.currentSessionOrNull()?.user
            val publicUser = client.from("users")
                .select {
                    filter {
                        User::id eq user!!.id
                    }
                }.decodeSingle<User>()
            KotPref.apply {
                this.id = publicUser.id.toString()
                this.username = publicUser.username
                this.email = publicUser.email
            }
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.toString()))
        }
    }

    override fun insertRecipe(
        recipe: Recipe
    ): Flow<ResponseState<Recipe>>{
        return flow {
            emit(ResponseState.Loading)
            try {
                ResponseState.Success(client.from("recipe").insert(recipe))
            } catch (e: Exception){
                emit(ResponseState.Error(e.message.toString()))
            }
        }
    }

    override fun updateRecipe(
        id: Int,
        recipeImg: String,
        recipeName: String,
        ingredients: String,
        steps: String
    ): Flow<ResponseState<Boolean>> {
        return flow {
            client.from("recipe").update(
                update = {
                    set("recipe_img", recipeImg)
                    set("recipe_name", recipeName)
                    set("ingredients", ingredients)
                    set("steps", steps)
                },
                request = {
                    filter {
                        eq("id", id)
                    }
                }
            )
        }
    }

}