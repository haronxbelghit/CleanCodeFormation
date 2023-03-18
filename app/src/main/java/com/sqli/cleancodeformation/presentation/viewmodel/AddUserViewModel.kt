package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(private val addUserUseCase: AddUserUseCase) :
    ViewModel() {

    fun addUser(username: String, profilePictureUrl: String) {
        val user = User(
            username = username,
            profilePictureUrl = profilePictureUrl
        )
        viewModelScope.launch {
            addUserUseCase(user)
        }
    }
}
