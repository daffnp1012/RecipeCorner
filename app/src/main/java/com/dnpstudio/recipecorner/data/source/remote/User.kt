package com.dnpstudio.recipecorner.data.source.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("username") val username: String,
    @SerialName("img_url") val imageUrl: String?,
    @SerialName("email") val email: String
)
