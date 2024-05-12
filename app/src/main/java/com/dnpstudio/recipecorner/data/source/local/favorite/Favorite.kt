package com.dnpstudio.recipecorner.data.source.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val favRecipeName: String
)
