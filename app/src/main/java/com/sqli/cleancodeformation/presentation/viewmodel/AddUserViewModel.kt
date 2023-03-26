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

    fun addUser(username: String, profilePictureUri: String) {
        val user = User(
            username = username,
            profilePictureUri = profilePictureUri
        )
        viewModelScope.launch {
            addUserUseCase(user)
            sharedViewModel.onUserAdded()
        }
    }
}
