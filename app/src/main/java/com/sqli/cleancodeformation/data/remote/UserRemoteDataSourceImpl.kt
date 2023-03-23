package com.sqli.cleancodeformation.data.remote

import com.sqli.cleancodeformation.data.local.entity.UserEntity
import com.sqli.cleancodeformation.data.remote.api.UserApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRemoteDataSource {

    override suspend fun getUsers(): List<UserEntity> {
        val response = apiService.getUsers()
        val userEntityList = response.data.map {
            UserEntity(
                id = it.id,
                username = "${it.firstName} ${it.lastName}",
                profilePictureUri = it.avatar
            )
        }
        return userEntityList
    }
}
