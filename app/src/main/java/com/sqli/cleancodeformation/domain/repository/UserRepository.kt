package com.sqli.cleancodeformation.domain.repository

import androidx.lifecycle.LiveData
import com.sqli.cleancodeformation.domain.model.User


interface UserRepository {

    suspend fun getAllUsers(): LiveData<List<User>>
    suspend fun addUser(user: User)
}

