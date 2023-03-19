package com.sqli.cleancodeformation.data.local

import com.sqli.cleancodeformation.data.local.dao.UserDao
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

}
