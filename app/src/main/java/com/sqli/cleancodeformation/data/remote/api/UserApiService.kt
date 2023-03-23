package com.sqli.cleancodeformation.data.remote.api

import com.sqli.cleancodeformation.data.remote.model.UserApiResponse
import retrofit2.http.GET

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): UserApiResponse
}
