package com.sqli.cleancodeformation.domain.repository

import com.sqli.cleancodeformation.data.local.entity.UserEntity
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.model.fromDomain
import com.sqli.cleancodeformation.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepoImpl @Inject constructor(
    private val localUserRepository: com.sqli.cleancodeformation.data.local.UserRepository
) : UserRepository {

    override suspend fun getAllUsers(): Flow<List<User>> {
        val userEntityList = localUserRepository.getAllUsers()
        val userEntityListConv: Flow<List<User>> =
            userEntityList.map { userEList: List<UserEntity> ->
                userEList.map { it.toDomain() }
            }
        return userEntityListConv
    }

    override suspend fun addUser(user: User) {
        val userEntity = user.fromDomain()
        localUserRepository.insert(userEntity)
    }

    override suspend fun getUserById(id: Int): User {
        return localUserRepository.getUserById(id).toDomain()
    }
}