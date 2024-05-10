package com.dnpstudio.recipecorner.data.repository

import android.util.Log
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import com.dnpstudio.recipecorner.data.tables.SupabaseTables
import com.dnpstudio.recipecorner.preference.LocalUser
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
import io.ktor.client.request.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : RecipeRepository {

    private val recipeChannel = client.channel("recipe")

    override suspend fun unsubcribeChannel() {
        recipeChannel.unsubscribe()
        client.realtime.removeChannel(recipeChannel)
    }

    override suspend fun getRecipe(): Result<Flow<List<Recipe>>> {
        val data = recipeChannel.postgresListDataFlow(
            schema = "public",
            table = SupabaseTables.RECIPE_TABLE,
            primaryKey = Recipe::id
        ).flowOn(Dispatchers.IO)

        return Result.success(data)
    }

    override suspend fun getRecipeDetail(): Result<Flow<Recipe>> {
        val data = recipeChannel.postgresSingleDataFlow(
            schema = "public",
            table = SupabaseTables.RECIPE_TABLE,
            primaryKey = Recipe::id
        ) {}

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
            val publicUser = client.from("Users")
                .select {
                    filter {
                        User::id eq user?.id
                    }
                }.decodeSingle<User>()
            LocalUser.apply {
                this.id = publicUser.id.toString()
                this.username = publicUser.username
            }
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.toString()))
            Log.d("REPO", e.toString())
        }

    }

    suspend fun login(email: String, password: String) {

        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        val user = client.auth.currentSessionOrNull()?.user
        val publicUser = client.postgrest["users"]
            .select {
                filter {
                    User::id eq user!!.id
                }
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

//    override fun updateRecipe(
//        recipeName: String,
//        recipeImg: String,
//        ingredients: String,
//        steps: String
//    ): Flow<ResponseState<Recipe>>{
//        return flow{
//            client.from("recipe").update{
//                update = {
//                    set
//                }
//                request {
//
//                }
//            }
//        }
//    }

}