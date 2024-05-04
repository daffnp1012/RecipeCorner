package com.dnpstudio.recipecorner.data.repository

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
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

    

}