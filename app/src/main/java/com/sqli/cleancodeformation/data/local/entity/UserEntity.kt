package com.sqli.cleancodeformation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var username: String,
    var profilePictureUrl: String
)