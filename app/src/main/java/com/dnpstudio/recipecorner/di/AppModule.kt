package com.dnpstudio.recipecorner.di

import android.app.Application
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.repository.RecipeRepositoryImpl
import com.dnpstudio.recipecorner.data.source.local.favorite.FavoriteDao
import com.dnpstudio.recipecorner.data.source.local.favorite.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = "https://hefbofshsjuivrjeiokm.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhlZmJvZnNoc2p1aXZyamVpb2ttIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcxMjExMTYyNywiZXhwIjoyMDI3Njg3NjI3fQ.fvZwmrByCVfRDfCxOjQ-L9fR_dcvCRxBhv2U53khNzw"
        ){
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }

    @Provides
    @Singleton
    fun provideRepository(
        supabaseClient: SupabaseClient,
        favoriteDatabase: FavoriteDatabase
    ): RecipeRepository{
        return RecipeRepositoryImpl(
            client = supabaseClient,
            favoriteDatabase = favoriteDatabase
        )
    }

    @Provides
    @Singleton
    fun provideFavouriteDatabase(
        app: Application
    ): FavoriteDatabase{
        return FavoriteDatabase.getInstance(app)
    }

}