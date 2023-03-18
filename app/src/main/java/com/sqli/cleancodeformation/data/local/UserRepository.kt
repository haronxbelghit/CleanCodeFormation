package com.sqli.cleancodeformation.data.local

import androidx.lifecycle.LiveData
import com.sqli.cleancodeformation.data.local.dao.UserDao
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    val allUsers: LiveData<List<UserEntity>> = userDao.getAllUsers()

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

}
