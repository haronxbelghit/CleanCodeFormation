package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val sharedViewModel: UserSharedViewModel
) :
    ViewModel() {

    fun addUser(
        firstName: String,
        profilePictureUri: String,
        lastName: String,
        city: String,
        country: String,
        job: String,
        desc: String,
        phone: String,
        tel: String,
        email: String
    ) {
        val user = User(
            firstName = firstName,
            profilePictureUri = profilePictureUri,
            lastName = lastName,
            city = city,
            country = country,
            job = job,
            desc = desc,
            phone = phone,
            tel = tel,
            email = email,
        )
        viewModelScope.launch {
            addUserUseCase(user)
            sharedViewModel.onUserAdded()
        }
    }
}
