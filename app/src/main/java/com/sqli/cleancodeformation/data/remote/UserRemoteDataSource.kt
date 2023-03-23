package com.sqli.cleancodeformation.data.remote

import com.sqli.cleancodeformation.data.local.entity.UserEntity

interface UserRemoteDataSource {
    suspend fun getUsers(): List<UserEntity>
}
