package com.sqli.cleancodeformation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var firstName: String,
    var lastName: String,
    var city: String?,
    var country: String?,
    var job: String?,
    var desc: String?,
    var phone: String?,
    var tel: String?,
    var email: String,
    var profilePictureUri: String,
)
