package com.sqli.cleancodeformation.data.remote

import com.sqli.cleancodeformation.data.local.entity.UserEntity
import com.sqli.cleancodeformation.data.remote.api.UserApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRemoteDataSource {

    override suspend fun getUsers(): List<UserEntity> {
        val response1 = apiService.getUsers(1)
        val response2 = apiService.getUsers(2)
        val response = response1.data + response2.data
        val userEntityList = response.map {
            UserEntity(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                profilePictureUri = it.avatar,
                city = null,
                country = null,
                job = null,
                desc = null,
                phone = null,
                tel = null,
                email = it.email,
            )
        }
        return userEntityList
    }
}
