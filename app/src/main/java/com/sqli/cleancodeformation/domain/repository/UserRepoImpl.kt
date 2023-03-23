package com.sqli.cleancodeformation.domain.repository

import com.sqli.cleancodeformation.data.UserDataSource
import com.sqli.cleancodeformation.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepoImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getAllUsers(): Flow<List<User>> {
        return userDataSource.getUsers()
    }


    override suspend fun addUser(user: User) {
        userDataSource.addUser(user)
    }

    override suspend fun getUserById(id: Int): User {
        return userDataSource.getUserById(id)
    }
}
