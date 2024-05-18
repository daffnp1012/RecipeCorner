package com.dnpstudio.recipecorner.data.source.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String
)
