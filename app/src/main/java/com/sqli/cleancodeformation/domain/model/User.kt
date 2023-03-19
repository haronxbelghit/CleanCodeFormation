package com.sqli.cleancodeformation.domain.model

import com.sqli.cleancodeformation.data.local.entity.UserEntity

data class User(
    var id: Int? = 1,
    var username: String,
    var profilePictureUri: String
) {
    constructor() : this(null, "", "")
}

fun User.fromDomain(): UserEntity {
    return UserEntity(
        username = username, profilePictureUri = profilePictureUri
    )
}

fun UserEntity.toDomain(): User {
    return User(username = username, profilePictureUri = profilePictureUri)
}