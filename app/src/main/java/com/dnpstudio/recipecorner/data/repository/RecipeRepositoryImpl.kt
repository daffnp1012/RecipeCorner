package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import com.dnpstudio.recipecorner.data.tables.SupabaseTables
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
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor (
    private val client: SupabaseClient
): RecipeRepository {

    private val recipeChannel = client.channel("recipe")

    override suspend fun getRecipe(): Result<Flow<List<Recipe>>>{
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
        ){}

        return Result.success(data)
    }

    override suspend fun deleteRecipe(
        id: Int
    ): Recipe {
        return client.from("recipe").delete(
            request = {
               filter {
                   eq("id", id)
               }
            }
        ).decodeSingle<Recipe>()
    }
    override suspend fun register(username: String, email: String, password: String) {

        client.auth.signUpWith(Email){
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("username", username)
            }
        }

    }

    override suspend fun unsubcribeChannel() {
        recipeChannel.unsubscribe()
        client.realtime.removeChannel(recipeChannel)
    }

    suspend fun login(email: String, password: String) {

        client.auth.signInWith(Email){
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

}