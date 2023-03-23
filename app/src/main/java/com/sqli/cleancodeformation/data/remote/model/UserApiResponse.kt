package com.sqli.cleancodeformation.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserApiResponse(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<UserApiData>
)

data class UserApiData(
    val id: Int,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String
)
