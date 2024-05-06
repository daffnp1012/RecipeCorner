package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor (
    private val client: SupabaseClient
): RecipeRepository {

    override suspend fun getRecipe(): List<Recipe> {
        return client.from("recipe").select(Columns.ALL).decodeList<Recipe>()
    }

    override suspend fun getRecipeDetail(): Recipe {
        return client.from("recipe").select(Columns.ALL).decodeSingle<Recipe>()
    }

//    override suspend fun register(email: String, password: String): Email.Result? {
//
//        return client.auth.signUpWith(Email){
//            this.email = email
//            this.password = password
//        }
//
//    }

//    override suspend fun login(email: String, password: String) {
//        return client.auth.signInWith(Email){
//            this.email = email
//            this.password = password
//        }
//
//        val user = client.auth.currentSessionOrNull()?.user
//        val publicUser = client.postgrest["users"]
//            .select {
//                filter {
//                    User::id eq user!!.id
//                }
//            }
//
//    }

}