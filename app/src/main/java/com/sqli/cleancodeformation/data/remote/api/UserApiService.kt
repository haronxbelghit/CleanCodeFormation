package com.sqli.cleancodeformation.data.remote.api

import com.sqli.cleancodeformation.data.remote.model.UserApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): UserApiResponse
}
