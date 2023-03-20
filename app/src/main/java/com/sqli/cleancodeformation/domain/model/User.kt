package com.sqli.cleancodeformation.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import java.io.Serializable

data class User(
    var id: Int? = null,
    var username: String= "default_username",
    var profilePictureUri: String= "default_uri"
) : Serializable, Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(username)
        writeString(profilePictureUri)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}


fun User.fromDomain(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        profilePictureUri = profilePictureUri
    )
}


fun UserEntity.toDomain(): User {
    return User(
        id = id,
        username = username,
        profilePictureUri = profilePictureUri
    )
}
