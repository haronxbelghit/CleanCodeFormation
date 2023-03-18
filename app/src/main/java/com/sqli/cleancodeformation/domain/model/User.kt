package com.sqli.cleancodeformation.domain.model

import com.sqli.cleancodeformation.data.local.entity.UserEntity

data class User(
    var id: Int? = null,
    var username: String,
    var profilePictureUrl: String
) {
    constructor() : this(null, "", "")
}

fun User.fromDomain(): UserEntity {
    return UserEntity(
        username = username, profilePictureUrl = profilePictureUrl
    )
}

fun UserEntity.toDomain(): User {
    return User(username = username, profilePictureUrl = profilePictureUrl)
}