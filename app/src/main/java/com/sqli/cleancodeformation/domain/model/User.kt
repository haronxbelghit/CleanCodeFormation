package com.sqli.cleancodeformation.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.sqli.cleancodeformation.data.local.entity.UserEntity
import java.io.Serializable

data class User(
    var id: Int? = null,
    var profilePictureUri: String = "default",
    var firstName: String = "default",
    var lastName: String = "default",
    var city: String? = "default",
    var country: String? = "default",
    var job: String? = "default",
    var desc: String? = "default",
    var phone: String? = "default",
    var tel: String? = "default",
    var email: String = "default",
) : Serializable, Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(firstName)
        writeString(profilePictureUri)
        writeString(lastName)
        writeString(city)
        writeString(country)
        writeString(job)
        writeString(desc)
        writeString(phone)
        writeString(tel)
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
        firstName = firstName,
        lastName = lastName,
        profilePictureUri = profilePictureUri,
        city = city,
        country = country,
        job = job,
        desc = desc,
        phone = phone,
        tel = tel,
        email = email,
    )
}


fun UserEntity.toDomain(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        profilePictureUri = profilePictureUri,
        city = city,
        country = country,
        job = job,
        desc = desc,
        phone = phone,
        tel = tel,
        email = email,
    )
}
