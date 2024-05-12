package com.dnpstudio.recipecorner.data.source.local.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Favorite::class],
    version = 1
)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE : FavoriteDatabase? = null
        fun getInstance(context: Context): FavoriteDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    FavoriteDatabase::class.java,
                    "favorite.db"
                ).build()
            }
        }
    }

}