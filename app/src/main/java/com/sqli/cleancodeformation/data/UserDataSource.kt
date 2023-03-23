package com.sqli.cleancodeformation.data

import com.sqli.cleancodeformation.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun getUsers(): Flow<List<User>>
    suspend fun addUser(user: User)
    suspend fun getUserById(id: Int): User
}
