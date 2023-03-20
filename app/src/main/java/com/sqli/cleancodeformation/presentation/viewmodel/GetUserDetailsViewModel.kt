package com.sqli.cleancodeformation.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.domain.usecase.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserDetailsViewModel @Inject constructor(
    private val getUserDetails: GetUserDetailsUseCase,
) : ViewModel() {
    val selectedUser = MutableLiveData<User>()
    fun getUserById(id: Int): User {
        viewModelScope.launch {
            val result = getUserDetails.invoke(id)
            selectedUser.value = result
        }
        return selectedUser.value!!
    }
}