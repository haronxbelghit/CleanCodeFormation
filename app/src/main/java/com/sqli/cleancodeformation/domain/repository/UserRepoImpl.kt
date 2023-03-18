package com.sqli.cleancodeformation.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sqli.cleancodeformation.data.local.entity.UserEntity

import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.model.fromDomain
import com.sqli.cleancodeformation.domain.model.toDomain
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val localUserRepository: com.sqli.cleancodeformation.data.local.UserRepository
) : UserRepository {

    override suspend fun getAllUsers(): LiveData<List<User>> {
        val userEntityList = localUserRepository.allUsers
        val userEntityListConv: LiveData<List<User>> =
            userEntityList.map { userEList: List<UserEntity> ->
                userEList.map { it.toDomain() }
            }
        return userEntityListConv
    }

    override suspend fun addUser(user: User) {
        val userEntity = user.fromDomain()
        localUserRepository.insert(userEntity)
    }

}
