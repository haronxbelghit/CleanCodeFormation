package com.sqli.cleancodeformation.data

import com.sqli.cleancodeformation.data.local.UserRepository
import com.sqli.cleancodeformation.data.remote.UserRemoteDataSource
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.model.fromDomain
import com.sqli.cleancodeformation.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userEntityRepository: UserRepository,
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

    override suspend fun getUsersCount(): Int {
        return getUsers().toList().size
    }

    override suspend fun getUsersPaged(limit: Int, offset: Int): List<User> {
        val userRemoteEntityList = userRemoteDataSource.getUsers()

        userEntityRepository.insertAll(userRemoteEntityList)

        return userEntityRepository.getAllUsersPaged(limit, offset).map { it.toDomain() }
    }
}
