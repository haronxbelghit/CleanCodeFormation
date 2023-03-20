package com.sqli.cleancodeformation.domain.repository

import com.sqli.cleancodeformation.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    suspend fun getAllUsers(): Flow<List<User>>
    suspend fun addUser(user: User)
    suspend fun getUserById(id: Int): User

}

