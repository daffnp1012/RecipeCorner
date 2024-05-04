package com.dnpstudio.recipecorner.di

import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = "https://hefbofshsjuivrjeiokm.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhlZmJvZnNoc2p1aXZyamVpb2ttIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTIxMTE2MjcsImV4cCI6MjAyNzY4NzYyN30.eg11qvu_RCU-WYnWiFJfVOpl_rGx74XBAIro8zyF3oQ"
        ){
            install(Auth)
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideRepository(
        supabaseClient: SupabaseClient
    ): RecipeRepository{
        return RecipeRepositoryImpl(
            client = supabaseClient
        )
    }


}