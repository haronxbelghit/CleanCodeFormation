package com.sqli.cleancodeformation.data

import com.sqli.cleancodeformation.data.local.UserRepository
import com.sqli.cleancodeformation.data.remote.UserRemoteDataSource
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.model.fromDomain
import com.sqli.cleancodeformation.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userEntityRepository: UserRepository
) : UserDataSource {

    override suspend fun getUsers(): Flow<List<User>> {
        val userRemoteEntityList = userRemoteDataSource.getUsers()

        userEntityRepository.insertAll(userRemoteEntityList)

        val allUsersFlow = userEntityRepository.getAllUsers()

        return allUsersFlow.map { userEntityList ->
            userEntityList.map { it.toDomain() }
        }

    }

    override suspend fun addUser(user: User) {
        userEntityRepository.insert(user.fromDomain())
    }

    override suspend fun getUserById(id: Int): User {
        return userEntityRepository.getUserById(id).toDomain()
    }
}
